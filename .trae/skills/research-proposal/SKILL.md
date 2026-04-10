---
name: "research-proposal"
description: "Generate high-quality academic research proposals for PhD applications and grants following Nature Reviews-style academic writing conventions. Use when creating research proposals, PhD proposals, or grant applications."
license: "MIT"
tags: ["academic", "research", "proposal", "phd", "grant", "writing"]
source: "luwill/research-skills"
---

# Research Proposal Skill

Generate high-quality academic research proposals for PhD applications and grants following Nature Reviews-style academic writing conventions.

## When to Use This Skill

- You need to create a **PhD research proposal** for graduate school applications
- You need to write a **grant proposal** for funding applications
- You need to develop a research project plan for academic review
- You need a structured 5-phase workflow with quality checks

## Features

- **Structured 5-phase workflow**: Requirements → Literature → Outline → Content → Output
- **Multi-source literature integration**: WebSearch, Zotero MCP, arXiv, PubMed
- **Bilingual support**: English and Chinese (中文)
- **Domain adaptation**: STEM, Humanities, Social Sciences
- **Nature Reviews-style**: Academic writing with proper hedging language
- **Minimum 40 references** for comprehensive literature coverage
- **Output in Markdown** (easily convertible to DOCX/PDF via pandoc)

## 5-Phase Workflow

```mermaid
graph LR
    A[Phase 1: Gather Requirements] --> B[Phase 2: Collect Literature]
    B --> C[Phase 3: Generate Outline]
    C --> D[User Approval]
    D --> E[Phase 4: Write Full Proposal]
    E --> F[Phase 5: Quality Check & Output]
```

### Phase 1: Gather Requirements

Collect essential information:

- **Research topic**: What is the main research question?
- **Domain**: STEM / Humanities / Social Sciences
- **Language**: English / Chinese
- **Word count**: Target length (default ~3,000 words, 2,000-4,000 range)
- **Specific requirements**: Any institutional formatting requirements

### Phase 2: Collect Literature

Collect literature from multiple sources:

1. Web search for recent papers
2. Integrate Zotero library (if MCP available)
3. Pull from arXiv/PubMed (if MCP available)
4. Synthesize key findings and identify research gap
5. Minimum **40 references** required

### Phase 3: Generate Outline

Generate detailed outline for user approval:

- Title and abstract
- Introduction and research background
- Literature review and research gap
- Research questions and objectives
- Methodology and approach
- Expected results and significance
- Timeline and milestones
- References (preliminary)

### Phase 4: User Approval

Wait for user approval before writing full content. User may request modifications to outline.

### Phase 5: Write Full Proposal

Write complete proposal based on approved outline:

- Follow Nature Reviews academic writing style
- Use proper hedging language
- Maintain consistent citation format
- Include all required sections
- Generate quality checklist

## Structure Guide by Domain

### STEM Proposal

1. **Introduction** - Background and problem statement
2. **Literature Review** - Current state of the field
3. **Research Questions** - Specific, testable questions
4. **Methodology** - Experimental design, data analysis, computational methods
5. **Expected Outcomes** - Hypotheses, predicted results
6. **Significance** - Contributions to knowledge
7. **Timeline** - Gantt chart or milestone breakdown
8. **References**

### Humanities Proposal

1. **Introduction** - Research topic and intellectual context
2. **Literature Review** - Critical engagement with existing scholarship
3. **Research Questions** - Interpretive questions to explore
4. **Methodology** - Theoretical framework, analytical approach, sources
5. **Chapter by Chapter Outline** - Detailed content plan
6. **Significance** - Contribution to scholarly debate
7. **Timeline**
8. **References**

### Social Sciences Proposal

Combines elements from both:
- Clear research questions
- Mixed methods approach (if applicable)
- Theoretical framing
- Methodological transparency

## Writing Style Guidelines

### Nature Reviews Style
- **Prose-based**: Flowing narrative rather than bullet points
- **Hedging language**: "suggests", "indicates", "appears to", "may contribute"
- **Clear paragraph structure**: One main idea per paragraph
- **Proper citations**: Cite foundational and recent work
- **Logical progression**: Build argument from background to conclusion

### Length Targets
- **Default**: 2,000-4,000 words (~3,000 words typical)
- **Abstract**: 150-250 words
- **Introduction**: 10-15% of total
- **Literature Review**: 20-25% of total
- **Methodology**: 20-25% of total
- **Expected Results**: 15-20% of total

## Quality Checklist

Before completion, verify:

- [ ] All required sections present
- [ ] Minimum 40 references included
- [ ] Research question clearly stated
- ** [ ] Methodology appropriate for question
- [ ] Timeline realistic
- [ ] Significance clearly articulated
- [ ] Citations consistent format
- [ ] Writing style academic but accessible
- [ ] No vague or undefined terms
- [ ] Potential limitations acknowledged

## Output Format

```
proposal/
├── README.md                 # Project overview
├── OUTLINE.md               # Detailed outline
├── PROPOSAL.md              # Full research proposal (Markdown)
├── QUALITY_CHECK.md         # Quality verification checklist
└── REFERENCES.bib          # BibTeX format references (optional)
```

Convert to Word/PDF:
```bash
pandoc -s PROPOSAL.md -o PROPOSAL.docx
# or
pandoc -s PROPOSAL.md -o PROPOSAL.pdf
```

## Starting Prompt Example

```
Generate a PhD research proposal on "AI ethics in healthcare decision-making"
Domain: Social Sciences, Language: Chinese, Word count: 3000
```

The skill will:
1. Gather requirements confirmation
2. Collect literature from multiple sources
3. Generate detailed outline
4. Wait for your approval
5. Write complete proposal after approval
6. Deliver final output with quality checklist

## Integration with MCP

If MCP servers available:
- **Zotero**: Import your existing library references
- **arXiv**: Search recent preprints
- **PubMed**: Search biomedical literature
- **WebSearch**: Find latest publications

Without MCP:
- Uses web search directly
- Still produces high-quality proposal
