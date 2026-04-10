---
name: "stat-writing"
description: "Statistical paper writing workbench for LaTeX. Supports abstract drafting, manuscript audits, reviewer reports, point-by-point responses, outline expansion, and book scaffolding. Journal-agnostic with optional JDS alignment. Use when writing statistical papers in LaTeX."
license: "CC0-1.0"
tags: ["statistics", "writing", "latex", "paper", "academic", "reviewer", "response"]
source: "fuhaoda/stats-paper-writing-agent-skills"
---

# Statistical Writing Workbench

One-skill workbench for statistical paper writing in LaTeX. Supports common statistical writing tasks with automated checking and templates.

## When to Use This Skill

- You need to draft an abstract and keywords for a statistical paper
- You want a full manuscript audit for structure, references, notation, reproducibility
- You need to write a referee report for a journal submission
- You need to draft a point-by-point response to reviewers
- You want to expand an outline into complete LaTeX prose
- You need a book manuscript scaffold from a chapter plan

## Features

- Front matter drafting (title, abstract, keywords)
- Manuscript audits (structure, refs, notation, reproducibility cues)
- Reviewer report drafting
- Point-by-point response letter templates
- Outline-to-section expansion
- Book manuscript scaffolding
- Journal-agnostic by default
- Optional JDS (Journal of Data Science) aligned profile for review-readiness checks
- Includes Python scripts for automated checking

## Commonly Used Tasks

### 1. Draft Abstract + Keywords

**Usage:**
```
Use stat-writing. Read main.tex and draft:
(1) a submission-ready abstract (default 6-8 sentences, acceptable 4-10; no citations; no math notation),
(2) 6-10 keywords in alphabetical order.

Output LaTeX blocks for \begin{abstract}...\end{abstract} and \keywords{...}.
Then include a short compliance checklist.
```

### 2. Full Manuscript Audit

**Usage:**
```
Use stat-writing. Audit main.tex (+ refs.bib if available).
Run scripts/check_tex.py and scripts/check_bib.py if possible.
Return top issues ranked HIGH/MED/LOW, each with location + why it matters + concrete LaTeX edits.
```

The skill will check:
- Abstract constraints (length, citations, math)
- Keyword order/duplicates
- Labels and cross-references
- Missing float captions/labels
- Line-number readiness for review
- Raster figure usage warnings
- Hard-coded references ("Figure 2" without ref)
- Reproducibility/supplement cue absence
- Citation style issues
- English/Unicode common pitfalls

### 3. Draft Reviewer Report

**Usage:**
```
Use stat-writing. Draft a referee report with:
- summary paragraph,
- overall assessment,
- numbered major comments,
- numbered minor comments,
- optional confidential note to editor.
```

### 4. Response to Reviewers

**Usage:**
```
Use stat-writing. Draft a point-by-point response letter.
Start with overview of major changes, then sections for Editor/Associate Editor/Reviewers.
For each comment: quote verbatim, respond, quote revised text, and give change location.
```

### 5. Outline to Section Expansion

**Usage:**
```
Use stat-writing. Expand this Methods outline into complete LaTeX prose.
Do not invent results or citations; add \todo{...} placeholders for missing details.
```

### 6. Book Manuscript Scaffold

**Usage:**
```
Use stat-writing. Build a book manuscript starter from this chapter plan.
Use frontmatter/mainmatter/backmatter, create chapter placeholders, and keep notation generic unless I ask for custom macros.
```

## Included Templates

| Template | Path | Description |
|----------|------|-------------|
| Manuscript Template | `assets/manuscript-template.tex` | Basic statistical paper template |
| Book Template | `assets/book-manuscript-template.tex` | Book scaffolding template |
| Reviewer Report | `assets/reviewer-report-template.md` | Report structure template |
| Reviewer Report (LaTeX) | `assets/reviewer-report-template.tex` | LaTeX format report |
| Response Letter | `assets/response-letter-template.tex` | Point-by-point response template |
| Section Skeleton | `assets/section-skeleton.tex` | Empty section with common structures |

## Optional Python Scripts

The skill includes checking scripts you can run:

```bash
# Check LaTeX manuscript for common issues
python skills/stat-writing/scripts/check_tex.py path/to/main.tex

# Check bibliography for common issues
python skills/stat-writing/scripts/check_bib.py --tex path/to/main.tex --bib path/to/refs.bib

# Full audit
python skills/stat-writing/scripts/audit_paper.py --tex path/to/main.tex --bib path/to/refs.bib
```

## What the Scripts Check

### check_tex.py flags these heuristics:
- Abstract length constraints
- Keyword order/duplicates/redundancy
- Labels and cross-references completeness
- Missing float caption/label
- Line-number readiness
- Raster figure usage warnings
- Hard-coded figure references
- Possible missing equation punctuation
- Reproducibility/supplement cue absence
- Citation style usage hints
- Selected English/unicode pitfalls

### check_bib.py flags these heuristics:
- Cited-but-missing keys
- Unused bib entries
- Page range formatting
- Missing common entry fields
- Duplicate DOI values
- Author fields containing literal "et al."
- Title capitalization risk warnings

## Journal Alignment

- **Default**: Journal-agnostic, works for any statistics journal
- **Optional JDS alignment**: When requested, enables Journal of Data Science specific review-readiness checks

## Working with Existing Manuscripts

When auditing or modifying an existing manuscript:
1. Read the current .tex file completely
2. Understand the existing structure and notation
3. Follow the existing citation/style conventions
4. Only suggest changes that improve compliance with statistical writing norms
5. Keep your changes minimal relative to the existing content
