---
name: "gstack"
description: "Garry Tan's gstack - transforms Claude Code into a virtual engineering team with 23 specialized roles. Invoke when starting new projects, doing code reviews, planning features, or shipping production code."
---

# gstack - Garry Tan's Claude Code Setup

gstack turns Claude Code into a virtual engineering team with 23 specialized roles: CEO, Designer, Eng Manager, Release Manager, Doc Engineer, QA, Security Officer, and more.

## Available Skills (Slash Commands)

### Planning & Strategy
- `/office-hours` - Describe what you're building, get guidance
- `/plan-ceo-review` - Product strategy review from CEO perspective
- `/plan-eng-review` - Engineering architecture review
- `/plan-design-review` - UI/UX design review
- `/plan-devex-review` - Developer experience review
- `/autoplan` - Automatic planning for features
- `/careful` - Double-check work for critical changes
- `/learn` - Learn about the codebase

### Design
- `/design-consultation` - Design consultation
- `/design-shotgun` - Quick design exploration
- `/design-html` - Design HTML prototypes
- `/design-review` - Design review

### Review & Quality
- `/review` - Code review on current branch
- `/cso` - Chief Security Officer audit (OWASP + STRIDE)
- `/devex-review` - Developer experience review
- `/benchmark` - Performance benchmarking

### QA & Testing
- `/qa` - QA testing on staging URL
- `/qa-only` - QA only mode
- `/connect-chrome` - Connect Chrome browser for testing
- `/setup-browser-cookies` - Setup browser cookies

### Browsing
- `/browse` - Browse web (replaces default mcp tools)

### Release & Deployment
- `/ship` - Ship the PR
- `/land-and-deploy` - Land and deploy
- `/canary` - Canary deployment
- `/setup-deploy` - Setup deployment
- `/document-release` - Document the release

### Project Management
- `/retro` - Retro on recent work
- `/investigate` - Investigate issues
- `/codex` - Codex knowledge capture
- `/freeze` - Freeze changes to prevent accidental edits
- `/guard` - Add guardrails to files
- `/unfreeze` - Unfreeze changes
- `/gstack-upgrade` - Upgrade gstack to latest version

## Installation

gstack is installed globally at `~/.claude/skills/gstack` and auto-updates in team mode.

## Who This Is For

- Founders and CEOs who still want to ship
- First-time Claude Code users who want structured roles
- Tech leads and staff engineers wanting rigorous review, QA, and release automation

## Quick Start

1. Run `/office-hours` and describe what you're building
2. Run `/plan-ceo-review` on any feature idea
3. Run `/review` on any branch with changes
4. Run `/qa` on your staging URL

Learn more at: https://github.com/garrytan/gstack
