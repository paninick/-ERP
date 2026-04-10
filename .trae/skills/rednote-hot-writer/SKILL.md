---
name: "rednote-hot-writer"
description: "Rednote/Notion hot writer - Proficient in Rednote/Notion markdown writing syntax, helps you write beautiful notes/documents with proper formatting, hotkeys, callouts, toggles, tables, embedded content. Use when writing notes/documents in Rednote/Notion."
license: "MIT"
tags: ["markdown", "writing", "rednote", "notion", "notes"]
---

# Rednote/Notion Hot Writer

Proficient in **Rednote/Notion** markdown writing syntax, helps you write beautiful notes/documents with proper formatting, hotkeys, callouts, toggles, tables, embedded content.

## Supported Syntax

### 1. Heading hierarchy

```markdown
# H1 - Main Title
## H2 - Section
### H3 - Subsection
#### H4 - Sub-subsection
```

### 2. Text Formatting

- **bold text** `**bold**`
- *italic text* `*italic*`
- ***bold italic*** `***bold italic***`
- `inline code` `` `inline code` ``
- ~~strikethrough~~ `~~strikethrough~~`
- [link text](url) `[link text](url)`
- ==highlight== `==highlight==`
- `$数学公式$`

### 3. Lists

#### Unordered List
- Item 1
- Item 2
  - Nested item 1
  - Nested item 2

#### Ordered List
1. First item
2. Second item
3. Third item

### 4. Task List (Toggles)
- [x] Completed task
- [ ] Incomplete task
- [ ] Pending task

### 5. Callouts / Admonitions

```markdown
> [!info]
> Here's a callout block.
```

**Supported callout types:**

| Type | Syntax | Color | Usage |
|------|--------|-------|------|
| info | `[!info]` | blue | General information |
| tip | `[!tip]` | green | Tips and hints |
| warning | `[!warning]` | yellow | Warning something to pay attention |
| danger | `[!danger]` | red | Danger / error |
| question | `[!question]` | blue | Question to answer |
| quote | `[!quote]` | neutral | Quote / citation |

### 6. Tables

| Syntax | Description |
|--------|-------------|
| Header | Title |
| Paragraph | Text |

### 7. Code Blocks

\`\`\`language
code content here
\`\`\`

### 8. Blockquotes

> blockquote text
> continued line

### 9. Horizontal Rules

---

### 10. Images

![alt text](image.jpg)

### 11. Embedded Content

Rednote supports embedding:
- YouTube videos
- Twitter tweets  
- PDF documents
- Other web content

## When Writing Notes

### Principles
1. **Use proper heading hierarchy** - # for title, ## for sections, ### for subsections
2 **Group by topic** - One idea per group, don't mix everything together
3. **Use callouts for important information** - info/tip/warning according to content
4. **Use task lists** for tracking todos
5. **Keep it readable** - Don't overformat, keep clean

### Common Templates

### Daily Note Template
```markdown
# {date}

## Morning
- 

## Afternoon
- 

## Notes
- 
```

### Meeting Notes Template
```markdown
# Meeting: {topic} - {date}

## Attendees
- [ ] Name 1
- [ ] Name 2

## Agenda
- 

## Notes
- 

## Action Items
- [ ] @Name - Action item
- [ ] @Name - Action item

## Decisions
- 
```

### Weekly Report Template
Use the `weekly-report` skill together with this, it will integrate nicely.

## Usage

When you need to write notes/documents in Rednote/Notion:
1. User provides raw content
2. You format it properly according to Rednote/Notion markdown syntax
3. Add correct callouts, toggles, tables where appropriate
4. Output clean readable markdown that's ready to paste

