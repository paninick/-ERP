---
name: "office-pdf"
description: "PDF processing skills for Claude Code: fill forms, merge documents, convert formats, extract data. Use when working with PDF files - filling fillable forms, merging multiple PDFs, converting PPTX to PDF, extracting data from forms."
license: "MIT"
tags: ["office", "pdf", "document", "form-filling", "merge", "convert"]
source: "tfriedel/claude-office-skills"
---

# Office PDF Processing Skill

PDF processing workflows for Claude Code: form filling, document merging, format conversion, data extraction.

## When to Use This Skill

- You need to **fill a fillable PDF form** with data from JSON/CSV
- You need to **merge multiple PDF documents** into one
- You need to **convert PPTX to PDF** or **PDF to images**
- You need to **extract data** from PDF forms or documents
- You need to **fill non-fillable PDF** with text annotations at precise positions

## Key Capabilities

- **Form filling** - Populate fillable PDFs programmatically
- **Document merging** - Combine multiple PDFs into single document
- **Format conversion** - PPTX → PDF, PDF → page images
- **Data extraction** - Pull field values from PDF forms
- **Non-fillable filling** - Detect fields and add text annotations

## Workflow: Fill Fillable PDF Form

```mermaid
graph TD
    A[Input PDF + Data JSON] --> B[Extract Form Fields]
    B --> C[Validate Fields]
    C --> D[Fill Data]
    D --> E[Flatten (optional)]
    E --> F[Output Filled PDF]
```

### Step-by-step:

1. **Extract form fields**
   ```bash
   python public/pdf/scripts/extract-form.py input.pdf output/fields.json
   ```
   - Extracts all fillable fields with names and positions
   - Saves to JSON for filling

2. **Fill fields with data**
   ```bash
   python public/pdf/scripts/fill-form.py input.pdf data.json output/filled.pdf
   ```
   - Fills each field with provided data
   - Preserves form fields if you want to keep them fillable
   - Optionally flattens to finalize

3. **Validate output**
   - Check all fields filled correctly
   - Verify no missing data

## Workflow: Merge Multiple PDFs

```bash
python public/pdf/scripts/merge-pdfs.py "file1.pdf" "file2.pdf" "file3.pdf" output/merged.pdf
```

Supports any number of PDFs, merges in order provided.

## Workflow: Convert PPTX to PDF

Uses LibreOffice for reliable conversion:

```bash
libreoffice --headless --convert-to pdf --outdir output input.pptx
```

## Workflow: Convert PDF to Images

Uses poppler-utils (pdftoppm):

```bash
python public/pdf/scripts/pdf-to-images.py input.pdf output/images/ 200
```

- Converts each page to PNG
- DPI configurable (default 200)
- Useful for previewing or sharing

## Available Scripts

| Script | Purpose |
|--------|---------|
| `extract-form.py` | Extract fillable form fields to JSON |
| `fill-form.py` | Fill form fields from JSON data |
| `merge-pdfs.py` | Merge multiple PDFs into one |
| `pdf-to-images.py` | Convert PDF pages to PNG images |

## Output Organization

```
outputs/<project-name>/
├── original.pdf             # Backup of original
├── fields.json             # Extracted form fields
├── filled.pdf              # Final filled PDF
├── merged.pdf              # Merged output
└── images/                # Extracted page images (if converted)
```

## Example Usage

**User:**
```
Fill out this application form.pdf with data from applicant-data.json
```

The skill will:
1. Extract form fields from the PDF
2. Match data from JSON to form fields
3. Fill all fields
4. Output filled PDF ready for submission

**User:**
```
Merge these three PDFs: report.pdf, appendices.pdf, cover.pdf into one complete-thesis.pdf
```

The skill will:
1. Merge all three PDFs in order
2. Output merged single PDF

## Prerequisites

- Python dependencies: `pip install -r requirements.txt` (pdfrw, PyPDF2)
- System tools:
  - **LibreOffice** (soffice) - for PPTX → PDF conversion
  - **poppler-utils** (pdftoppm) - for PDF → image conversion

Works with other office skills (pptx/docx/xlsx) for complete document automation workflow.
