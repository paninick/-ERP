# AI Agent 开发核心技能手册

**手册版本**: v1.0  
**创建日期**: 2026-04-02  
**知识来源**: sanbuphy/learn-coding-agent 项目深度分析

---

## 一、核心原则

### 1.1 渐进式架构原则

> **不要一开始就追求完美，从最小可行产品开始**

| 阶段 | 功能 | 时间 |
|------|------|------|
| MVP | s01 + s02 + s06 + 基础权限 | 1 周 |
| 生产就绪 | s03 + s04 + s05 + s07 + 完整权限 | 2-3 周 |
| 高级功能 | s08 - s12 | 3-6 周 |

### 1.2 工具优先原则

> **好的工具系统是 Agent 能力的核心**

工具系统设计优先级：
1. ✅ 工具接口标准化
2. ✅ 工厂模式创建工具
3. ✅ 注册表管理工具
4. ✅ 输入验证
5. ✅ 权限检查
6. ✅ 能力标识（并发安全、只读等）

### 1.3 用户体验优先原则

> **权限、持久化、恢复功能对生产使用至关重要**

必须实现的功能：
- ✅ 权限分层系统
- ✅ 会话持久化（JSONL）
- ✅ 会话恢复机制
- ✅ 交互式权限确认
- ✅ 操作审计日志

---

## 二、12 层架构应用指南

### 2.1 s01 - THE LOOP（必须首先实现）

**核心模式**:
```
用户输入 → messages[] → API → 响应
                      ↓
              stop_reason == "tool_use"?
             /                      \
          yes                       no
           ↓                         ↓
      执行工具                  返回文本
      添加 tool_result
      循环回到 → messages[]
```

**实现要点**:
- 使用 `while True` 循环
- 检查 `stop_reason` 字段
- 处理 `tool_calls` 数组
- 追加 `tool_result` 消息

### 2.2 s02 - TOOL DISPATCH（核心能力）

**工厂模式**:
```python
def build_tool(
    name: str,
    description: str,
    validate_fn: Callable,
    check_perms_fn: Callable,
    call_fn: Callable,
    concurrency_safe: bool = False,
    read_only: bool = False
) -&gt; Tool:
    # 创建工具类
    # 提供安全默认值
```

**注册表模式**:
```python
class ToolRegistry:
    def register(self, tool: Tool)
    def get(self, name: str) -&gt; Optional[Tool]
    def list_tools(self) -&gt; List[Tool]
    def to_openai_format(self) -&gt; List[Dict]
```

### 2.3 s03 - PLANNING（提升完成率）

**核心工具**:
- `EnterPlanModeTool` - 进入计划模式
- `ExitPlanModeTool` - 退出计划模式
- `TodoWriteTool` - 列出任务步骤

**价值**: 任务完成率翻倍

### 2.4 s04 - SUB-AGENTS（上下文隔离）

**生成模式**:
| 模式 | 说明 |
|------|------|
| default | 进程内，共享对话 |
| fork | 子进程，新鲜 messages[]，共享文件缓存 |
| worktree | 隔离 git worktree + fork |
| remote | 桥接到远程 |

**价值**: 保持主对话清洁，拆分大任务

### 2.5 s05 - KNOWLEDGE ON DEMAND（按需加载）

**核心概念**:
- 通过 `tool_result` 注入知识
- 不通过系统提示
- `CLAUDE.md` 文件按目录延迟加载

**工具**: `SkillTool`

### 2.6 s06 - CONTEXT COMPRESSION（生产必需）

**三层策略**:
1. **autoCompact** - 令牌数超过阈值时触发，API 总结旧消息
2. **snipCompact** - 移除僵尸消息和陈旧标记
3. **contextCollapse** - 重构上下文提高效率

**实现要点**:
- 使用 `[COMPACT_BOUNDARY]` 标记
- 保留最近消息完整保真
- 旧消息总结为摘要

### 2.7 s07 - PERSISTENT TASKS（任务持久化）

**核心概念**:
- 基于文件的任务图
- 状态跟踪
- 依赖管理
- 持久化存储

### 2.8 s08 - BACKGROUND TASKS（后台任务）

**核心概念**:
- 守护线程运行命令
- 完成时注入通知
- Agent 继续思考

### 2.9 s09-s12 - 高级功能（可选）

- AGENT TEAMS - 持久化队友 + 异步邮箱
- TEAM PROTOCOLS - SendMessageTool + 请求-响应
- AUTONOMOUS AGENTS - 空闲循环 + 自动认领
- WORKTREE ISOLATION - 任务 + 目录隔离

---

## 三、工具系统设计模式

### 3.1 工具接口标准

```python
from abc import ABC, abstractmethod
from dataclasses import dataclass

@dataclass
class ToolContext:
    working_dir: str
    permissions: Dict[str, bool]

class Tool(ABC):
    name: str
    description: str
    
    @abstractmethod
    def validate_input(self, input_data: Dict) -&gt; bool:
        """验证输入，提前拒绝错误参数"""
        pass
    
    @abstractmethod
    def check_permissions(self, context: ToolContext) -&gt; bool:
        """工具特定授权检查"""
        pass
    
    @abstractmethod
    def call(self, input_data: Dict, context: ToolContext) -&gt; Any:
        """执行工具并返回结果"""
        pass
    
    def is_concurrency_safe(self) -&gt; bool:
        """能否并行运行"""
        return False
    
    def is_read_only(self) -&gt; bool:
        """无副作用"""
        return False
    
    def is_destructive(self) -&gt; bool:
        """不可逆操作"""
        return False
```

### 3.2 工具工厂实现

```python
def build_tool(
    name: str,
    description: str,
    validate_fn: Callable[[Dict], bool],
    check_perms_fn: Callable[[ToolContext], bool],
    call_fn: Callable[[Dict, ToolContext], Any],
    concurrency_safe: bool = False,
    read_only: bool = False
) -&gt; Tool:
    """工具工厂函数 - 提供安全默认值"""
    
    class BuiltTool(Tool):
        name = name
        description = description
        
        def validate_input(self, input_data: Dict) -&gt; bool:
            return validate_fn(input_data)
        
        def check_permissions(self, context: ToolContext) -&gt; bool:
            return check_perms_fn(context)
        
        def call(self, input_data: Dict, context: ToolContext) -&gt; Any:
            return call_fn(input_data, context)
        
        def is_concurrency_safe(self) -&gt; bool:
            return concurrency_safe
        
        def is_read_only(self) -&gt; bool:
            return read_only
    
    return BuiltTool()
```

### 3.3 工具注册表

```python
class ToolRegistry:
    def __init__(self):
        self._tools: Dict[str, Tool] = {}
    
    def register(self, tool: Tool):
        self._tools[tool.name] = tool
    
    def get(self, name: str) -&gt; Optional[Tool]:
        return self._tools.get(name)
    
    def list_tools(self) -&gt; List[Tool]:
        return list(self._tools.values())
    
    def to_openai_format(self) -&gt; List[Dict]:
        return [
            {
                "type": "function",
                "function": {
                    "name": tool.name,
                    "description": tool.description,
                    "parameters": {"type": "object", "properties": {}}
                }
            }
            for tool in self._tools.values()
        ]
```

---

## 四、权限系统最佳实践

### 4.1 权限决策流程

```
工具调用请求
    │
    ▼
validateInput() ───────────────────────────┐
    │                                        │
    ▼                                        │
PreToolUse Hooks ──────────────────────────┐
    │                                        │
    ▼                                        │
Permission Rules ──────────────────────────┐
│  alwaysAllowRules: 匹配 → 自动允许        │
│  alwaysDenyRules:   匹配 → 自动拒绝        │
│  alwaysAskRules:    匹配 → 询问           │
    │                                        │
    ▼                                        │
无规则匹配? → Interactive Prompt             │
    │                                        │
    ▼                                        │
checkPermissions() (工具特定)               │
    │                                        │
    ▼                                        │
APPROVED → tool.call()                      │
```

### 4.2 权限引擎实现

```python
from enum import Enum
from dataclasses import dataclass

class PermissionDecision(Enum):
    ALLOW_ONCE = "allow_once"
    ALLOW_ALWAYS = "allow_always"
    DENY = "deny"
    ASK = "ask"

@dataclass
class PermissionRule:
    tool_pattern: str
    decision: PermissionDecision
    priority: int = 0

class PermissionEngine:
    def __init__(self):
        self._rules: List[PermissionRule] = []
        self._always_allow: set = set()
        self._always_deny: set = set()
    
    def add_rule(self, rule: PermissionRule):
        self._rules.append(rule)
        self._rules.sort(key=lambda r: -r.priority)
    
    def add_always_allow(self, tool_name: str):
        self._always_allow.add(tool_name)
    
    def add_always_deny(self, tool_name: str):
        self._always_deny.add(tool_name)
    
    def decide(self, tool_name: str, tool_input: dict) -&gt; PermissionDecision:
        # 1. 检查始终拒绝
        if tool_name in self._always_deny:
            return PermissionDecision.DENY
        
        # 2. 检查始终允许
        if tool_name in self._always_allow:
            return PermissionDecision.ALLOW_ALWAYS
        
        # 3. 检查规则
        for rule in self._rules:
            if self._match_pattern(rule.tool_pattern, tool_name):
                return rule.decision
        
        # 4. 默认询问
        return PermissionDecision.ASK
    
    def _match_pattern(self, pattern: str, tool_name: str) -&gt; bool:
        if pattern == "*":
            return True
        if pattern.endswith("*"):
            return tool_name.startswith(pattern[:-1])
        return pattern == tool_name
```

---

## 五、会话持久化最佳实践

### 5.1 JSONL 日志格式

```jsonl
{"type":"user","content":"你好","timestamp":"2026-04-02T10:00:00"}
{"type":"assistant","content":"你好！有什么可以帮你的？","timestamp":"2026-04-02T10:00:01"}
{"type":"tool_use","tool_name":"file_read","input":{"path":"test.txt"},"timestamp":"2026-04-02T10:00:02"}
{"type":"tool_result","tool_name":"file_read","result":"文件内容...","timestamp":"2026-04-02T10:00:02"}
{"type":"system","subtype":"compact_boundary","timestamp":"2026-04-02T10:05:00"}
```

### 5.2 持久化策略

| 消息类型 | 写入策略 | 理由 |
|---------|---------|------|
| 用户消息 | 阻塞写入 (fsync) | 崩溃恢复 |
| 助手消息 | 即发即忘 (保序队列) | 性能优化 |
| 进度 | 内联写入 (下次查询去重) | 减少 I/O |
| 刷新 | 结果 yield / cowork 急切刷新 | 数据一致性 |

### 5.3 持久化实现

```python
import json
import os
from datetime import datetime
from typing import List, Dict, Any

class SessionPersistence:
    def __init__(self, storage_dir: str):
        self.storage_dir = storage_dir
        os.makedirs(storage_dir, exist_ok=True)
        self.current_session_id = self._generate_session_id()
        self._log_file = self._get_log_file()
    
    def _generate_session_id(self) -&gt; str:
        return datetime.now().strftime("%Y%m%d_%H%M%S")
    
    def _get_log_file(self) -&gt; str:
        return os.path.join(self.storage_dir, f"{self.current_session_id}.jsonl")
    
    def log_user_message(self, content: str):
        self._append_log({
            "type": "user",
            "content": content,
            "timestamp": datetime.now().isoformat()
        })
    
    def log_assistant_message(self, content: str):
        self._append_log({
            "type": "assistant",
            "content": content,
            "timestamp": datetime.now().isoformat()
        })
    
    def log_tool_use(self, tool_name: str, input_data: dict):
        self._append_log({
            "type": "tool_use",
            "tool_name": tool_name,
            "input": input_data,
            "timestamp": datetime.now().isoformat()
        })
    
    def log_tool_result(self, tool_name: str, result: Any):
        self._append_log({
            "type": "tool_result",
            "tool_name": tool_name,
            "result": result,
            "timestamp": datetime.now().isoformat()
        })
    
    def _append_log(self, entry: dict):
        # 用户消息阻塞写入（崩溃恢复）
        if entry["type"] == "user":
            with open(self._log_file, "a", encoding="utf-8") as f:
                f.write(json.dumps(entry, ensure_ascii=False) + "\n")
                f.flush()
                os.fsync(f.fileno())
        else:
            # 其他消息即发即忘
            with open(self._log_file, "a", encoding="utf-8") as f:
                f.write(json.dumps(entry, ensure_ascii=False) + "\n")
    
    def load_session(self, session_id: str) -&gt; List[Dict]:
        log_file = os.path.join(self.storage_dir, f"{session_id}.jsonl")
        messages = []
        with open(log_file, "r", encoding="utf-8") as f:
            for line in f:
                entry = json.loads(line)
                if entry["type"] == "user":
                    messages.append({"role": "user", "content": entry["content"]})
                elif entry["type"] == "assistant":
                    messages.append({"role": "assistant", "content": entry["content"]})
        return messages
    
    def list_sessions(self) -&gt; List[str]:
        sessions = []
        for filename in os.listdir(self.storage_dir):
            if filename.endswith(".jsonl"):
                sessions.append(filename[:-6])
        return sorted(sessions, reverse=True)
```

---

## 六、上下文压缩最佳实践

### 6.1 压缩策略

| 策略 | 触发条件 | 方法 |
|------|---------|------|
| autoCompact | 令牌数超过阈值 | API 总结旧消息 |
| snipCompact | HISTORY_SNIP 标志 | 移除僵尸消息 |
| contextCollapse | CONTEXT_COLLAPSE 标志 | 重构上下文 |

### 6.2 上下文窗口预算

```
┌─────────────────────────────────────────────────────┐
│  系统提示（工具、权限、CLAUDE.md）                  │
│  ══════════════════════════════════════════════      │
│                                                     │
│  对话历史                                           │
│  ┌─────────────────────────────────────────────┐    │
│  │ [旧消息的压缩摘要]                           │    │
│  │ ═══════════════════════════════════════════  │    │
│  │ [COMPACT_BOUNDARY 标记]                      │    │
│  │ ─────────────────────────────────────────── │    │
│  │ [最近消息 — 完整保真]                        │    │
│  │ user → assistant → tool_use → tool_result   │    │
│  └─────────────────────────────────────────────┘    │
│                                                     │
│  当前轮次（用户 + 助手响应）                        │
└─────────────────────────────────────────────────────┘
```

### 6.3 压缩实现

```python
from typing import List, Dict
import openai

class ContextCompressor:
    def __init__(self, api_key: str, token_threshold: int = 8000):
        self.client = openai.Client(api_key=api_key)
        self.token_threshold = token_threshold
        self.compact_boundary_marker = {"role": "system", "content": "[COMPACT_BOUNDARY]"}
    
    def count_tokens(self, messages: List[Dict]) -&gt; int:
        # 简化的 token 计数
        total = 0
        for msg in messages:
            total += len(msg.get("content", "")) // 4
        return total
    
    def should_compact(self, messages: List[Dict]) -&gt; bool:
        return self.count_tokens(messages) &gt; self.token_threshold
    
    def auto_compact(self, messages: List[Dict]) -&gt; List[Dict]:
        if not self.should_compact(messages):
            return messages
        
        # 找到压缩边界
        boundary_idx = self._find_compact_boundary(messages)
        
        if boundary_idx == -1:
            # 没有边界，压缩前半部分
            split_idx = len(messages) // 2
            old_messages = messages[:split_idx]
            recent_messages = messages[split_idx:]
        else:
            # 有边界，压缩边界之前的
            old_messages = messages[:boundary_idx]
            recent_messages = messages[boundary_idx + 1:]
        
        if not old_messages:
            return messages
        
        # 总结旧消息
        summary = self._summarize_messages(old_messages)
        
        # 构建新消息列表
        new_messages = [
            {"role": "system", "content": f"对话摘要: {summary}"},
            self.compact_boundary_marker
        ] + recent_messages
        
        return new_messages
    
    def _find_compact_boundary(self, messages: List[Dict]) -&gt; int:
        for i, msg in enumerate(messages):
            if msg.get("content") == "[COMPACT_BOUNDARY]":
                return i
        return -1
    
    def _summarize_messages(self, messages: List[Dict]) -&gt; str:
        conversation = "\n".join([
            f"{msg['role']}: {msg.get('content', '')}"
            for msg in messages
        ])
        
        response = self.client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[{
                "role": "user",
                "content": f"请总结以下对话，保留关键信息：\n\n{conversation}"
            }]
        )
        
        return response.choices[0].message.content
```

---

## 七、关键设计模式总结

| 模式 | 应用位置 | 目的 |
|------|----------|------|
| AsyncGenerator 流式 | QueryEngine, query() | 从 API 到消费者的全链流式 |
| Builder + Factory | buildTool() | 工具定义的安全默认值 |
| 品牌类型 | SystemPrompt, asSystemPrompt() | 防止字符串/数组混淆 |
| 特性标志 + DCE | feature() from bun:bundle | 编译时死代码消除 |
| 可区分联合 | Message 类型 | 类型安全的消息处理 |
| 观察者 + 状态机 | StreamingToolExecutor | 工具执行生命周期跟踪 |
| 快照状态 | FileHistoryState | 文件操作的撤销/重做 |
| 环形缓冲区 | 错误日志 | 长会话的有界内存 |
| 即发即忘写入 | recordTranscript() | 带排序的非阻塞持久化 |
| 延迟模式 | lazySchema() | 为性能延迟 Zod 模式评估 |
| 上下文隔离 | AsyncLocalStorage | 共享进程中每个 Agent 的上下文 |

---

## 八、常见陷阱与避免方法

### 8.1 架构陷阱

| 陷阱 | 避免方法 |
|------|---------|
| 过度设计 | 从 s01 开始，逐步添加 |
| 忽略权限 | 生产系统必须有权限控制 |
| 忘记持久化 | 用户需要恢复会话 |
| 跳过测试 | 工具系统需要充分测试 |
| 硬编码 | 使用配置和特性标志 |

### 8.2 实现陷阱

| 陷阱 | 症状 | 解决方案 |
|------|------|---------|
| 没有输入验证 | 工具执行时崩溃 | 在 checkPermissions 前 validateInput |
| 没有权限分层 | 用户误操作危险工具 | 实现 5 层权限流程 |
| 没有持久化 | 崩溃后丢失对话 | 实现 JSONL 日志 |
| 没有压缩 | 长对话超出 token 限制 | 实现 autoCompact |
| 没有特性标志 | 难以测试新功能 | 实现编译时 + 运行时标志 |

---

## 九、技术选型建议

### 9.1 推荐技术栈

| 组件 | 推荐选型 | 理由 |
|------|---------|------|
| 运行时 | Node.js / Bun | TypeScript 生态完善 |
| 语言 | TypeScript | 类型安全，可维护性高 |
| API 客户端 | OpenAI SDK | 官方支持，功能完善 |
| 数据存储 | JSONL / SQLite | 简单可靠，易于调试 |
| UI | React / Ink | 跨平台，组件化 |
| 并发 | Async/Await | 现代异步模式 |

### 9.2 替代方案

| 组件 | 替代方案 | 适用场景 |
|------|---------|---------|
| 语言 | Python | 快速原型、数据科学 |
| 运行时 | Node.js | 成熟生态、广泛部署 |
| 存储 | SQLite | 需要查询、事务支持 |
| API | Anthropic SDK | Claude 优先 |

---

## 十、问题诊断方法

### 10.1 工具执行问题

**检查清单**:
1. ✅ 输入验证是否通过？
2. ✅ 权限检查是否通过？
3. ✅ 工具特定权限是否通过？
4. ✅ 路径是否在沙箱内？
5. ✅ 文件是否存在？
6. ✅ 是否有读写权限？

### 10.2 上下文问题

**检查清单**:
1. ✅ 消息格式是否正确？
2. ✅ Token 数是否超限？
3. ✅ 是否需要压缩？
4. ✅ 系统提示是否完整？
5. ✅ 工具定义是否正确？

### 10.3 持久化问题

**检查清单**:
1. ✅ 存储目录是否存在？
2. ✅ 是否有写入权限？
3. ✅ JSONL 格式是否正确？
4. ✅ 会话 ID 是否有效？
5. ✅ 文件是否被其他进程锁定？

---

## 十一、性能优化技巧

### 11.1 工具执行优化

| 优化项 | 方法 | 收益 |
|--------|------|------|
| 并发执行 | StreamingToolExecutor 安全并发 | 多工具并行 |
| 懒加载 | lazySchema() 延迟模式评估 | 启动更快 |
| 缓存 | 工具结果缓存 | 重复调用更快 |

### 11.2 内存优化

| 优化项 | 方法 | 收益 |
|--------|------|------|
| 上下文压缩 | autoCompact | 减少内存占用 |
| 环形缓冲区 | 错误日志有界 | 内存可控 |
| 流式处理 | AsyncGenerator | 减少峰值内存 |

### 11.3 I/O 优化

| 优化项 | 方法 | 收益 |
|--------|------|------|
| 即发即忘 | 非用户消息异步写入 | 响应更快 |
| 阻塞写入 | 用户消息 fsync | 崩溃可恢复 |
| 批量写入 | 进度消息内联写入 | 减少 I/O |

---

## 十二、行业标准实践

### 12.1 安全实践

- ✅ 输入验证（所有输入）
- ✅ 权限分层（5 层检查）
- ✅ 路径沙箱（文件操作）
- ✅ 审计日志（所有操作）
- ✅ 特性标志（可远程禁用）

### 12.2 可靠性实践

- ✅ 会话持久化（JSONL）
- ✅ 崩溃恢复（阻塞写入用户消息）
- ✅ 错误处理（工具特定 try-catch）
- ✅ 重试逻辑（API 调用重试）
- ✅ 超时控制（工具执行超时）

### 12.3 可维护性实践

- ✅ 类型安全（TypeScript）
- ✅ 模块化设计（清晰目录结构）
- ✅ 工厂模式（buildTool）
- ✅ 注册表模式（ToolRegistry）
- ✅ 特性标志（灵活配置）

---

## 十三、快速参考卡片

### 13.1 实现优先级

| 优先级 | 功能 | 必须？ |
|--------|------|--------|
| 🔴 高 | s01 THE LOOP | ✅ |
| 🔴 高 | s02 TOOL DISPATCH | ✅ |
| 🔴 高 | s06 CONTEXT COMPRESSION | ✅ |
| 🔴 高 | 基础权限系统 | ✅ |
| 🔴 高 | 会话持久化 | ✅ |
| 🟡 中 | s03 PLANNING | 推荐 |
| 🟡 中 | s04 SUB-AGENTS | 推荐 |
| 🟡 中 | s05 KNOWLEDGE ON DEMAND | 推荐 |
| 🟡 中 | s07 PERSISTENT TASKS | 推荐 |
| 🟢 低 | s08-s12 高级功能 | 可选 |

### 13.2 核心接口速查

**Tool 接口**:
- `validate_input(input_data: dict) -&gt; bool`
- `check_permissions(context: ToolContext) -&gt; bool`
- `call(input_data: dict, context: ToolContext) -&gt; Any`

**SessionPersistence 接口**:
- `log_user_message(content: str)`
- `log_assistant_message(content: str)`
- `log_tool_use(tool_name: str, input: dict)`
- `log_tool_result(tool_name: str, result: Any)`
- `load_session(session_id: str) -&gt; List[Dict]`
- `list_sessions() -&gt; List[str]`

**PermissionEngine 接口**:
- `add_rule(rule: PermissionRule)`
- `add_always_allow(tool_name: str)`
- `add_always_deny(tool_name: str)`
- `decide(tool_name: str, tool_input: dict) -&gt; PermissionDecision`

---

## 十四、总结

### 14.1 关键要点

1. **渐进式架构** - 不要一开始就追求完美，从 s01 开始
2. **工具优先** - 好的工具系统是 Agent 能力的核心
3. **用户体验** - 权限、持久化、恢复对生产至关重要
4. **可扩展性** - 设计时考虑未来演进
5. **生产就绪** - 遥测、特性标志、远程控制是企业级必需

### 14.2 学习路径

1. **第一步**: 理解 12 层架构
2. **第二步**: 实现 s01 + s02 + s06 + 基础权限
3. **第三步**: 添加 s03 + s04 + s05 + s07 + 完整权限
4. **第四步**: 根据需要添加 s08-s12 高级功能

### 14.3 成功因素

- ✅ 从简单开始，逐步迭代
- ✅ 优先实现核心功能（工具、权限、持久化、压缩）
- ✅ 遵循设计模式（工厂、注册表、可区分联合等）
- ✅ 重视用户体验（权限确认、会话恢复、操作日志）
- ✅ 为未来扩展设计（子 Agent、MCP、团队协作）

---

**手册结束**

**版本**: v1.0  
**最后更新**: 2026-04-02  
**知识来源**: sanbuphy/learn-coding-agent 项目深度分析
