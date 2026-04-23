#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
ERP系统SQL注入检查脚本
检查Mapper XML文件中的SQL注入风险

用法:
    python scripts/sql-injection-check.py              # 检查默认路径
    python scripts/sql-injection-check.py -p <路径>     # 额外检查指定路径
"""

import os
import re
import sys
from pathlib import Path

# ---------------------------------------------------------------------------
# 预编译正则表达式
# ---------------------------------------------------------------------------

# ${xxx} 直接参数绑定（高危）
RE_DOLLAR_BINDING = re.compile(r'\$\{[^}]+\}')

# 各类动态 SQL 标签（支持跨行匹配）
RE_IF_TAG = re.compile(r'<if test="[^"]*">(.*?)</if>', re.DOTALL)
RE_FOREACH_TAG = re.compile(r'<foreach[^>]*>(.*?)</foreach>', re.DOTALL)
RE_WHEN_TAG = re.compile(r'<when test="[^"]*">(.*?)</when>', re.DOTALL)
RE_WHERE_TAG = re.compile(r'<where>(.*?)</where>', re.DOTALL)
RE_SET_TAG = re.compile(r'<set>(.*?)</set>', re.DOTALL)

# 字符串拼接风险：'string' + variable 或 variable + 'string'
# （在 SQL 上下文中暗示动态拼接 SQL 片段）
RE_CONCAT_RISK = re.compile(
    r"'[^']*'\s*\+\s*[\w$.{}]+|[\w$.{}]+\s*\+\s*'[^']*'"
)

# 所有标签模式，供 iter_tag_contents 迭代
_TAG_PATTERNS = {
    'if': RE_IF_TAG,
    'foreach': RE_FOREACH_TAG,
    'when': RE_WHEN_TAG,
    'where': RE_WHERE_TAG,
    'set': RE_SET_TAG,
}


# ---------------------------------------------------------------------------
# 辅助函数
# ---------------------------------------------------------------------------

def _line_number(content: str, offset: int) -> int:
    """返回 offset 对应的行号（从 1 开始）。"""
    return content[:offset].count('\n') + 1


def _preview(text: str, max_len: int = 60) -> str:
    """截取文本预览，替换换行符为空格。"""
    return text.replace('\n', ' ').strip()[:max_len]


# ---------------------------------------------------------------------------
# 扫描逻辑
# ---------------------------------------------------------------------------

def _scan_dollar_bindings(content: str, filepath: str,
                          issues: list) -> None:
    """扫描 ${} 绑定，将发现追加到 issues 列表。"""
    for m in RE_DOLLAR_BINDING.finditer(content):
        lineno = _line_number(content, m.start())
        issues.append(
            f"{filepath}:{lineno}: 发现高风险参数绑定 {m.group()}"
        )


def _scan_tag_contents(content: str, filepath: str,
                       issues: list) -> None:
    """遍历各类动态标签的内部文本，检查字符串拼接风险。"""
    for tag_name, pattern in _TAG_PATTERNS.items():
        for match in pattern.finditer(content):
            inner = match.group(1).strip()
            if not inner:
                continue

            concat_matches = RE_CONCAT_RISK.findall(inner)
            if concat_matches:
                lineno = _line_number(content, match.start())
                issues.append(
                    f"{filepath}:{lineno}: <{tag_name}>标签内发现字符串"
                    f"拼接风险: {_preview(inner)}..."
                )


# ---------------------------------------------------------------------------
# 核心检查函数
# ---------------------------------------------------------------------------

def check_mapper_sql_injection(mapper_root: str) -> list:
    """遍历 mapper_root 下的所有 XML，返回问题列表。"""
    issues: list = []

    for root, _dirs, files in os.walk(mapper_root):
        for filename in sorted(files):
            if not filename.endswith('.xml'):
                continue

            filepath = os.path.join(root, filename)

            try:
                with open(filepath, 'r', encoding='utf-8') as f:
                    content = f.read()
            except (IOError, UnicodeDecodeError) as exc:
                issues.append(f"{filepath}: 无法读取文件: {exc}")
                continue

            _scan_dollar_bindings(content, filepath, issues)
            _scan_tag_contents(content, filepath, issues)

    return issues


# ---------------------------------------------------------------------------
# 路径解析
# ---------------------------------------------------------------------------

def resolve_mapper_paths(project_root: str) -> list:
    """返回待扫描的 Mapper XML 目录列表。"""
    return [
        os.path.join(
            project_root,
            'ruoyi-admin/src/main/resources/mapper/erp',
        ),
        os.path.join(
            project_root,
            'ruoyi-demo/src/main/resources/mapper/demo',
        ),
    ]


# ---------------------------------------------------------------------------
# CLI 入口
# ---------------------------------------------------------------------------

def parse_argv(argv: list) -> list:
    """简单参数解析（不使用 argparse 以减少外部依赖）。

    返回额外需要扫描的路径列表。
    """
    extra_paths: list = []
    i = 1
    while i < len(argv):
        if argv[i] in ('-p', '--path'):
            i += 1
            if i < len(argv):
                extra_paths.append(argv[i])
            else:
                print("错误: --path 缺少参数")
                sys.exit(2)
        else:
            print(f"错误: 未知参数 {argv[i]}")
            sys.exit(2)
        i += 1
    return extra_paths


def main() -> int:
    """主函数"""
    extra_paths = parse_argv(sys.argv)

    # 基于 __file__ 解析项目根路径
    script_dir = Path(__file__).resolve().parent       # scripts/
    project_root = script_dir.parent                   # RuoYi-Vue/
    mapper_paths = resolve_mapper_paths(str(project_root))
    mapper_paths.extend(extra_paths)

    print("开始检查ERP系统SQL注入风险...")

    all_issues: list = []

    for mapper_path in mapper_paths:
        if os.path.exists(mapper_path):
            print(f"检查路径: {mapper_path}")
            issues = check_mapper_sql_injection(mapper_path)
            all_issues.extend(issues)
        else:
            print(f"路径不存在: {mapper_path}")

    if all_issues:
        print(f"\n发现 {len(all_issues)} 个SQL注入风险:")
        for issue in all_issues:
            print(f"  - {issue}")
        print("\n建议:")
        print("  1. 将 ${} 参数绑定改为 #{}")
        print("  2. 避免在动态SQL中进行字符串拼接")
        print("  3. 使用MyBatis的参数绑定功能")
        return 1

    print("\n[OK] 未发现明显的SQL注入风险")
    return 0


if __name__ == '__main__':
    sys.exit(main())
