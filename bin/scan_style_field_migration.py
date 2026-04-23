#!/usr/bin/env python3
"""Scan the repository for style_no/style_code migration hotspots."""

from __future__ import annotations

import json
from collections import Counter
from dataclasses import dataclass
from datetime import datetime
from pathlib import Path
from typing import Iterable


ROOT = Path(__file__).resolve().parents[1]
OUTPUT_MD = ROOT / "doc" / "style-field-migration-scan.md"
OUTPUT_JSON = ROOT / "doc" / "style-field-migration-scan.json"

SCAN_ROOTS = [
    ROOT / "ruoyi-admin",
    ROOT / "ruoyi-ui",
    ROOT / "sql",
]

EXCLUDED_DIR_NAMES = {
    ".git",
    ".idea",
    ".backup",
    "node_modules",
    "dist",
    "archive",
    "logs",
    "target",
}

KEYWORDS = {
    "style_no": "db_legacy_column",
    "styleNo": "java_or_vue_legacy_property",
    "style_code": "db_target_column",
    "styleCode": "java_or_vue_target_property",
}

GROUP_RULES = [
    ("sql", ("sql/",)),
    ("backend-domain", ("ruoyi-admin/src/main/java/com/ruoyi/erp/domain/",)),
    ("backend-controller", ("ruoyi-admin/src/main/java/com/ruoyi/erp/controller/",)),
    ("backend-mapper-java", ("ruoyi-admin/src/main/java/com/ruoyi/erp/mapper/",)),
    ("backend-mapper-xml", ("ruoyi-admin/src/main/resources/mapper/erp/",)),
    ("frontend-view", ("ruoyi-ui/src/views/erp/",)),
    ("frontend-api", ("ruoyi-ui/src/api/erp/",)),
]


@dataclass
class FileHit:
    path: str
    group: str
    counts: dict[str, int]
    status: str


def iter_files() -> Iterable[Path]:
    for scan_root in SCAN_ROOTS:
        if not scan_root.exists():
            continue
        for path in scan_root.rglob("*"):
            if not path.is_file():
                continue
            if any(part in EXCLUDED_DIR_NAMES for part in path.parts):
                continue
            yield path


def classify_group(relative_path: str) -> str:
    normalized = relative_path.replace("\\", "/")
    for group, prefixes in GROUP_RULES:
        if normalized.startswith(prefixes):
            return group
    return "other"


def classify_status(counts: Counter) -> str:
    has_legacy = counts["style_no"] > 0 or counts["styleNo"] > 0
    has_target = counts["style_code"] > 0 or counts["styleCode"] > 0
    if has_legacy and has_target:
        return "mixed"
    if has_legacy:
        return "legacy-only"
    if has_target:
        return "target-only"
    return "clean"


def scan_file(path: Path) -> FileHit | None:
    normalized_path = path.as_posix()
    if normalized_path.startswith((ROOT / "ruoyi-admin" / "src" / "main" / "java").as_posix()) and path.suffix == ".vue":
        return None

    try:
        content = path.read_text(encoding="utf-8")
    except UnicodeDecodeError:
        content = path.read_text(encoding="utf-8", errors="ignore")

    counts = Counter()
    for needle in KEYWORDS:
        counts[needle] = content.count(needle)

    if not any(counts.values()):
        return None

    relative_path = path.relative_to(ROOT).as_posix()
    return FileHit(
        path=relative_path,
        group=classify_group(relative_path),
        counts=dict(counts),
        status=classify_status(counts),
    )


def build_summary(hits: list[FileHit]) -> dict[str, object]:
    group_summary: dict[str, dict[str, object]] = {}
    for hit in hits:
        entry = group_summary.setdefault(
            hit.group,
            {
                "files": 0,
                "legacy_only": 0,
                "target_only": 0,
                "mixed": 0,
                "style_no": 0,
                "styleNo": 0,
                "style_code": 0,
                "styleCode": 0,
            },
        )
        entry["files"] += 1
        entry[hit.status.replace("-", "_")] += 1
        for key, value in hit.counts.items():
            entry[key] += value

    return {
        "generated_at": datetime.now().isoformat(timespec="seconds"),
        "root": str(ROOT),
        "totals": {
            "files": len(hits),
            "legacy_only": sum(1 for item in hits if item.status == "legacy-only"),
            "target_only": sum(1 for item in hits if item.status == "target-only"),
            "mixed": sum(1 for item in hits if item.status == "mixed"),
        },
        "group_summary": group_summary,
        "files": [hit.__dict__ for hit in hits],
    }


def render_markdown(summary: dict[str, object]) -> str:
    lines: list[str] = []
    lines.append("# Style Field Migration Scan")
    lines.append("")
    lines.append(f"- Generated at: `{summary['generated_at']}`")
    lines.append(f"- Root: `{summary['root']}`")
    lines.append("")

    totals = summary["totals"]
    lines.append("## Totals")
    lines.append("")
    lines.append(f"- Files with style field hits: `{totals['files']}`")
    lines.append(f"- Legacy only: `{totals['legacy_only']}`")
    lines.append(f"- Target only: `{totals['target_only']}`")
    lines.append(f"- Mixed: `{totals['mixed']}`")
    lines.append("")

    lines.append("## Group Summary")
    lines.append("")
    lines.append("| Group | Files | Legacy Only | Target Only | Mixed | style_no | styleNo | style_code | styleCode |")
    lines.append("| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |")
    for group, entry in sorted(summary["group_summary"].items()):
        lines.append(
            "| {group} | {files} | {legacy_only} | {target_only} | {mixed} | {style_no} | {styleNo} | {style_code} | {styleCode} |".format(
                group=group,
                **entry,
            )
        )
    lines.append("")

    lines.append("## File Hits")
    lines.append("")
    lines.append("| Status | Group | File | style_no | styleNo | style_code | styleCode |")
    lines.append("| --- | --- | --- | ---: | ---: | ---: | ---: |")
    for item in sorted(summary["files"], key=lambda row: (row["status"], row["group"], row["path"])):
        counts = item["counts"]
        lines.append(
            f"| {item['status']} | {item['group']} | `{item['path']}` | "
            f"{counts['style_no']} | {counts['styleNo']} | {counts['style_code']} | {counts['styleCode']} |"
        )
    lines.append("")

    lines.append("## Suggested Batch Order")
    lines.append("")
    lines.append("1. Migrate `legacy-only` files in low-conflict backend/frontend modules.")
    lines.append("2. Add compatibility SQL for tables still using `style_no`.")
    lines.append("3. Resolve `mixed` files last to avoid breaking in-flight parallel work.")
    lines.append("")
    return "\n".join(lines)


def main() -> None:
    hits = [item for item in (scan_file(path) for path in iter_files()) if item]
    summary = build_summary(hits)

    OUTPUT_JSON.write_text(json.dumps(summary, indent=2, ensure_ascii=False), encoding="utf-8")
    OUTPUT_MD.write_text(render_markdown(summary), encoding="utf-8")

    print(f"Scan complete: {len(hits)} files hit")
    print(f"Markdown report: {OUTPUT_MD}")
    print(f"JSON report: {OUTPUT_JSON}")


if __name__ == "__main__":
    main()
