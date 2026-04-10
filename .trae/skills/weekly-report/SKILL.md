---
name: "weekly-report"
description: "Weekly work report generator - Generate professional weekly work reports from your work log. Organizes your work into clear structured format with achievements, problems, next week plan. Use when you need to write a weekly work report."
license: "MIT"
tags: ["weekly-report", "report", "work", "productivity", "documentation"]
---

# Weekly Work Report Generator

Generate professional **weekly work reports** from your work description. Organizes your work into a clear structured format that's ready to send to your team/manager.

## When to Use

- You have completed a week of work and need to write a weekly report
- You have a list of work items you did this week
- You want a professional structured format that's easy to read
- You need to summarize achievements, problems, and next week plan

## Output Structure

Follow this standard structure:

# [Week X, YYYY] Weekly Work Report

## 一、本周完成工作

### 1. [项目名称]
- ✅ [Completed work item 1] - [brief description]
- ✅ [Completed work item 2] - [brief description]
- 🔄 [In-progress work item] - [progress description]

### 2. [Another project]
...

## 二、遇到的问题及解决方案

- ❌ [Problem description] - [solution you found]
- ⚠️ [Problem description] - [still working on it / plan next week]

## 三、下周工作计划

- [ ] [Work item 1] - [planned description]
- [ ] [Work item 2] - [planned description]

## Guidelines

1. **Be honest** - If something is still in progress, say so. Don't pretend everything is done
2. **Be specific** - Give brief descriptions that show what you actually did
- Bold the project names for readability
3. **Be organized** - Group work by project, don't mix everything together
4. **Include problems** - It's normal to have problems, showing you've solved them demonstrates problem-solving ability
5. **Plan ahead** - Clear next week plan helps coordination

## Usage Example

**User input:**
```
This week I worked on:
1. Fixed the login bug in user center
2. Developed the new report export feature
3. Started the refactoring of database query layer, not finished yet
4. Helped testing the mobile app version
```

**Generated output:**

# Week 18, 2025 Weekly Work Report

## 一、本周完成工作

### 🁢 用户中心
- ✅ 修复登录bug - 修复了token过期导致无法跳转的问题，现在已测试通过
- ✅ 开发报表导出功能 - 完成Excel导出接口和前端导出按钮开发
- ✅ 协助移动端测试 - 配合测试团队验证了几个兼容性问题

### 🁢 基础架构
- 🔄 数据库查询层重构 - 完成约60%，本周主要重构了查询构建器，下周继续

## 二、遇到的问题及解决方案

- ❌ 导出大数据量时内存溢出 - 解决方案：改成流式导出，已解决
- ⚠️ 移动端兼容性问题 - 还在排查中，下周继续定位

## 三、下周工作计划

- [ ] 完成数据库查询层重构
- [ ] 上线报表导出功能
- [ ] 开始用户权限管理模块开发
- [ ] 继续协助移动端测试

## When user provides raw work log, the skill automatically organizes it into this clean professional structure ready to send.
