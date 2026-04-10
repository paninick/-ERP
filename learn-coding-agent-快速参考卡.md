# learn-coding-agent 快速参考卡

## 📋 目录
1. [已创建的文档](#已创建的文档)
2. [12 层架构速查](#12-层架构速查)
3. [核心设计模式](#核心设计模式)
4. [关键模块接口](#关键模块接口)
5. [实现路线图](#实现路线图)

---

## 📁 已创建的文档

| 文件名 | 内容 | 用途 |
|--------|------|------|
| `learn-coding-agent-深度分析报告.md` | 完整的架构分析 | 理解 Claude Code 设计 |
| `learn-coding-agent-实战学习指南.md` | 可运行的 Python 代码 | 从零构建自己的 Agent |
| `learn-coding-agent-快速参考卡.md` | 本文件 | 快速查阅关键内容 |

---

## 🏗️ 12 层架构速查

| 层级 | 名称 | 核心概念 | 优先级 |
|------|------|---------|--------|
| s01 | THE LOOP | while-true + API + 工具 | 🔴 高 |
| s02 | TOOL DISPATCH | buildTool() 工厂 + 注册表 | 🔴 高 |
| s03 | PLANNING | TodoWriteTool + 步骤列表 | 🟡 中 |
| s04 | SUB-AGENTS | fork + 新鲜 messages[] | 🟡 中 |
| s05 | KNOWLEDGE ON DEMAND | SkillTool + CLAUDE.md | 🟡 中 |
| s06 | CONTEXT COMPRESSION | autoCompact + 边界标记 | 🔴 高 |
| s07 | PERSISTENT TASKS | 任务图 + 状态跟踪 | 🟡 中 |
| s08 | BACKGROUND TASKS | 守护线程 + 通知 | 🟢 低 |
| s09 | AGENT TEAMS | 持久化队友 + 邮箱 | 🟢 低 |
| s10 | TEAM PROTOCOLS | SendMessageTool + 请求-响应 | 🟢 低 |
| s11 | AUTONOMOUS AGENTS | 空闲循环 + 自动认领 | 🟢 低 |
| s12 | WORKTREE ISOLATION | 任务 + 目录隔离 | 🟢 低 |

---

## 💡 核心设计模式

### 1. 工具工厂模式
```typescript
buildTool({
  name: "tool_name",
  description: "...",
  validateFn: (input) => bool,
  checkPermsFn: (ctx) => bool,
  callFn: (input, ctx) => result,
  concurrencySafe: bool,
  readOnly: bool
})
```

### 2. 权限决策流程
```
validateInput() 
    ↓
PreToolUse Hooks 
    ↓
Permission Rules (alwaysAllow/alwaysDeny/alwaysAsk)
    ↓
(无规则?) → Interactive Prompt
    ↓
checkPermissions() (工具特定)
    ↓
APPROVED → tool.call()
```

### 3. 上下文压缩策略
1. **autoCompact** - 超过阈值时 API 总结
2. **snipCompact** - 移除僵尸消息
3. **contextCollapse** - 重构上下文

---

## 🔌 关键模块接口

### Tool 接口
```python
class Tool:
    name: str
    description: str
    
    # 生命周期
    validate_input(input_data: dict) -> bool
    check_permissions(context: ToolContext) -> bool
    call(input_data: dict, context: ToolContext) -> Any
    
    # 能力
    is_concurrency_safe() -> bool
    is_read_only() -> bool
    is_destructive() -> bool
```

### SessionPersistence 接口
```python
class SessionPersistence:
    log_user_message(content: str)        # 阻塞写入
    log_assistant_message(content: str)   # 即发即忘
    log_tool_use(tool_name: str, input: dict)
    log_tool_result(tool_name: str, result: Any)
    load_session(session_id: str) -> List[Dict]
    list_sessions() -> List[str]
```

### PermissionEngine 接口
```python
class PermissionEngine:
    add_rule(rule: PermissionRule)
    add_always_allow(tool_name: str)
    add_always_deny(tool_name: str)
    decide(tool_name: str, tool_input: dict) -> PermissionDecision
```

---

## 📅 实现路线图

### 第 1 周 - MVP（最小可行产品）
- ✅ s01: THE LOOP - 基础 Agent 循环
- ✅ s02: TOOL DISPATCH - 工具系统
- ✅ s06: CONTEXT COMPRESSION - 上下文压缩
- ✅ 基础权限系统

### 第 2-3 周 - 生产就绪
- ✅ s03: PLANNING - 计划模式
- ✅ s04: SUB-AGENTS - 子 Agent
- ✅ s05: KNOWLEDGE ON DEMAND - 按需知识
- ✅ s07: PERSISTENT TASKS - 持久化任务
- ✅ 完整权限系统

### 第 4-6 周 - 高级功能
- ✅ s08-s12: 后台任务、团队协作、自主 Agent 等

---

## 🛠️ 技术栈建议

| 组件 | 推荐 | 替代方案 |
|------|------|---------|
| 语言 | TypeScript | Python |
| 运行时 | Bun | Node.js |
| API | OpenAI SDK | Anthropic SDK |
| 存储 | JSONL | SQLite |
| UI | React + Ink | 自定义 CLI |

---

## ⚠️ 常见陷阱

1. ❌ **过度设计** - 从 s01 开始，不要追求完美
2. ❌ **忽略权限** - 生产系统必须有权限控制
3. ❌ **忘记持久化** - 用户需要恢复会话
4. ❌ **跳过测试** - 工具系统需要充分测试
5. ❌ **硬编码** - 使用配置和特性标志

---

## 📚 快速开始

### 最小 Agent (s01)
```python
from minimal_agent import MinimalAgent
agent = MinimalAgent(api_key="...")
response = agent.run("你好！")
```

### 带工具的 Agent (s02)
```python
from agent_with_tools import AgentWithTools
agent = AgentWithTools(api_key="...", working_dir=".")
```

### 全功能 Agent
```python
from main import FullFeaturedAgent
agent = FullFeaturedAgent(
    api_key="...",
    working_dir=".",
    storage_dir="./sessions"
)
```

---

## 🎯 关键要点

1. **渐进式架构** - 从简单开始，逐步添加
2. **工具优先** - 好的工具系统是核心
3. **用户体验** - 权限、持久化、恢复很重要
4. **可扩展性** - 设计时考虑未来
5. **生产就绪** - 遥测、特性标志、远程控制

---

**参考卡版本**: v1.0  
**最后更新**: 2026-04-02
