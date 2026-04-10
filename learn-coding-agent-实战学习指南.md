# learn-coding-agent 实战学习指南

**学习日期**：2026-04-02  
**指南版本**：v1.0  

---

## 一、从理论到实践：构建自己的 Agent 系统

### 1.1 最小可行 Agent (MVA) - 第一步

让我们从最简单的 Agent 循环开始，这是 s01 层的实现。

```python
# minimal_agent.py - 最小可行 Agent
from typing import List, Dict, Any
import openai

class MinimalAgent:
    def __init__(self, api_key: str):
        self.client = openai.Client(api_key=api_key)
        self.messages: List[Dict] = []
    
    def run(self, user_input: str) -> str:
        # 1. 添加用户消息
        self.messages.append({"role": "user", "content": user_input})
        
        while True:
            # 2. 调用 API
            response = self.client.chat.completions.create(
                model="gpt-4",
                messages=self.messages,
                tools=self._get_tools()
            )
            
            message = response.choices[0].message
            self.messages.append(message.model_dump())
            
            # 3. 检查是否需要使用工具
            if not message.tool_calls:
                return message.content
            
            # 4. 执行工具
            for tool_call in message.tool_calls:
                result = self._execute_tool(tool_call)
                self.messages.append({
                    "role": "tool",
                    "tool_call_id": tool_call.id,
                    "content": str(result)
                })
    
    def _get_tools(self) -&gt; List[Dict]:
        return []
    
    def _execute_tool(self, tool_call) -&gt; Any:
        return "Tool executed"
```

### 1.2 测试 MVA

```python
# 使用示例
agent = MinimalAgent(api_key="your-key")
response = agent.run("你好！")
print(response)
```

---

## 二、工具系统实现 - s02 层

### 2.1 工具工厂模式

```python
# tool_system.py - 工具系统
from dataclasses import dataclass
from typing import Any, Callable, Optional, Dict
from abc import ABC, abstractmethod

@dataclass
class ToolContext:
    working_dir: str
    permissions: Dict[str, bool]

class Tool(ABC):
    name: str
    description: str
    
    @abstractmethod
    def validate_input(self, input_data: Dict) -&gt; bool:
        pass
    
    @abstractmethod
    def check_permissions(self, context: ToolContext) -&gt; bool:
        pass
    
    @abstractmethod
    def call(self, input_data: Dict, context: ToolContext) -&gt; Any:
        pass
    
    def is_concurrency_safe(self) -&gt; bool:
        return False
    
    def is_read_only(self) -&gt; bool:
        return False

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

### 2.2 创建具体工具

```python
# file_tools.py - 文件操作工具
import os

def create_file_read_tool() -&gt; Tool:
    def validate(input_data: Dict) -&gt; bool:
        return "path" in input_data
    
    def check_perms(context: ToolContext) -&gt; bool:
        return context.permissions.get("read_files", False)
    
    def call(input_data: Dict, context: ToolContext) -&gt; str:
        path = os.path.join(context.working_dir, input_data["path"])
        with open(path, "r", encoding="utf-8") as f:
            return f.read()
    
    return build_tool(
        name="file_read",
        description="读取文件内容",
        validate_fn=validate,
        check_perms_fn=check_perms,
        call_fn=call,
        concurrency_safe=True,
        read_only=True
    )

def create_file_write_tool() -&gt; Tool:
    def validate(input_data: Dict) -&gt; bool:
        return "path" in input_data and "content" in input_data
    
    def check_perms(context: ToolContext) -&gt; bool:
        return context.permissions.get("write_files", False)
    
    def call(input_data: Dict, context: ToolContext) -&gt; str:
        path = os.path.join(context.working_dir, input_data["path"])
        with open(path, "w", encoding="utf-8") as f:
            f.write(input_data["content"])
        return f"Written to {path}"
    
    return build_tool(
        name="file_write",
        description="写入文件内容",
        validate_fn=validate,
        check_perms_fn=check_perms,
        call_fn=call,
        concurrency_safe=False,
        read_only=False
    )
```

### 2.3 集成工具系统到 Agent

```python
# agent_with_tools.py
from tool_system import ToolRegistry, ToolContext
from file_tools import create_file_read_tool, create_file_write_tool

class AgentWithTools(MinimalAgent):
    def __init__(self, api_key: str, working_dir: str):
        super().__init__(api_key)
        self.registry = ToolRegistry()
        self.context = ToolContext(
            working_dir=working_dir,
            permissions={"read_files": True, "write_files": True}
        )
        
        # 注册工具
        self.registry.register(create_file_read_tool())
        self.registry.register(create_file_write_tool())
    
    def _get_tools(self) -&gt; List[Dict]:
        return self.registry.to_openai_format()
    
    def _execute_tool(self, tool_call) -&gt; Any:
        import json
        tool = self.registry.get(tool_call.function.name)
        if not tool:
            return f"Unknown tool: {tool_call.function.name}"
        
        input_data = json.loads(tool_call.function.arguments)
        
        # 验证输入
        if not tool.validate_input(input_data):
            return "Invalid input"
        
        # 检查权限
        if not tool.check_permissions(self.context):
            return "Permission denied"
        
        # 执行工具
        return tool.call(input_data, self.context)
```

---

## 三、权限系统实现 - 生产必需

### 3.1 权限规则引擎

```python
# permission_system.py
from dataclasses import dataclass
from typing import List, Callable, Optional
from enum import Enum

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

class UserPromptInterface:
    def ask_permission(self, tool_name: str, tool_input: dict) -&gt; PermissionDecision:
        print(f"\n工具请求: {tool_name}")
        print(f"输入: {tool_input}")
        print("选项:")
        print("  1) 允许一次")
        print("  2) 始终允许")
        print("  3) 拒绝")
        
        while True:
            choice = input("请选择 (1-3): ").strip()
            if choice == "1":
                return PermissionDecision.ALLOW_ONCE
            elif choice == "2":
                return PermissionDecision.ALLOW_ALWAYS
            elif choice == "3":
                return PermissionDecision.DENY
            print("无效选择，请重试")
```

### 3.2 集成权限系统

```python
# agent_with_permissions.py
from permission_system import PermissionEngine, UserPromptInterface, PermissionDecision

class AgentWithPermissions(AgentWithTools):
    def __init__(self, api_key: str, working_dir: str):
        super().__init__(api_key, working_dir)
        self.perm_engine = PermissionEngine()
        self.ui = UserPromptInterface()
    
    def _execute_tool(self, tool_call) -&gt; Any:
        import json
        tool = self.registry.get(tool_call.function.name)
        if not tool:
            return f"Unknown tool: {tool_call.function.name}"
        
        input_data = json.loads(tool_call.function.arguments)
        
        # 1. 验证输入
        if not tool.validate_input(input_data):
            return "Invalid input"
        
        # 2. 权限决策
        decision = self.perm_engine.decide(tool.name, input_data)
        
        if decision == PermissionDecision.ASK:
            decision = self.ui.ask_permission(tool.name, input_data)
        
        if decision == PermissionDecision.DENY:
            return "Permission denied"
        
        if decision == PermissionDecision.ALLOW_ALWAYS:
            self.perm_engine.add_always_allow(tool.name)
        
        # 3. 工具特定权限检查
        if not tool.check_permissions(self.context):
            return "Permission denied"
        
        # 4. 执行工具
        return tool.call(input_data, self.context)
```

---

## 四、会话持久化 - 崩溃恢复

### 4.1 JSONL 日志存储

```python
# persistence.py
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

### 4.2 集成持久化

```python
# agent_with_persistence.py
from persistence import SessionPersistence

class AgentWithPersistence(AgentWithPermissions):
    def __init__(self, api_key: str, working_dir: str, storage_dir: str):
        super().__init__(api_key, working_dir)
        self.persistence = SessionPersistence(storage_dir)
    
    def run(self, user_input: str) -&gt; str:
        # 记录用户消息（阻塞写入）
        self.persistence.log_user_message(user_input)
        
        # 添加到 messages
        self.messages.append({"role": "user", "content": user_input})
        
        while True:
            response = self.client.chat.completions.create(
                model="gpt-4",
                messages=self.messages,
                tools=self._get_tools()
            )
            
            message = response.choices[0].message
            self.messages.append(message.model_dump())
            
            if message.content:
                self.persistence.log_assistant_message(message.content)
            
            if not message.tool_calls:
                return message.content
            
            for tool_call in message.tool_calls:
                import json
                input_data = json.loads(tool_call.function.arguments)
                self.persistence.log_tool_use(tool_call.function.name, input_data)
                
                result = self._execute_tool(tool_call)
                self.persistence.log_tool_result(tool_call.function.name, result)
                
                self.messages.append({
                    "role": "tool",
                    "tool_call_id": tool_call.id,
                    "content": str(result)
                })
    
    def resume_session(self, session_id: str):
        self.messages = self.persistence.load_session(session_id)
        self.persistence.current_session_id = session_id
```

---

## 五、上下文压缩 - 处理长对话

### 5.1 压缩策略实现

```python
# context_compression.py
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

### 5.2 集成压缩系统

```python
# agent_with_compression.py
from context_compression import ContextCompressor

class FullFeaturedAgent(AgentWithPersistence):
    def __init__(self, api_key: str, working_dir: str, storage_dir: str):
        super().__init__(api_key, working_dir, storage_dir)
        self.compressor = ContextCompressor(api_key)
    
    def run(self, user_input: str) -&gt; str:
        self.persistence.log_user_message(user_input)
        self.messages.append({"role": "user", "content": user_input})
        
        while True:
            # 执行压缩
            self.messages = self.compressor.auto_compact(self.messages)
            
            response = self.client.chat.completions.create(
                model="gpt-4",
                messages=self.messages,
                tools=self._get_tools()
            )
            
            message = response.choices[0].message
            self.messages.append(message.model_dump())
            
            if message.content:
                self.persistence.log_assistant_message(message.content)
            
            if not message.tool_calls:
                return message.content
            
            for tool_call in message.tool_calls:
                import json
                input_data = json.loads(tool_call.function.arguments)
                self.persistence.log_tool_use(tool_call.function.name, input_data)
                
                result = self._execute_tool(tool_call)
                self.persistence.log_tool_result(tool_call.function.name, result)
                
                self.messages.append({
                    "role": "tool",
                    "tool_call_id": tool_call.id,
                    "content": str(result)
                })
```

---

## 六、完整示例：使用全功能 Agent

```python
# main.py - 完整示例
import os

def main():
    # 配置
    api_key = os.getenv("OPENAI_API_KEY")
    working_dir = "."
    storage_dir = "./sessions"
    
    # 创建 Agent
    agent = FullFeaturedAgent(
        api_key=api_key,
        working_dir=working_dir,
        storage_dir=storage_dir
    )
    
    # 配置权限规则
    agent.perm_engine.add_always_allow("file_read")
    agent.perm_engine.add_rule(PermissionRule(
        tool_pattern="file_write",
        decision=PermissionDecision.ASK,
        priority=10
    ))
    
    # 交互循环
    print("=== AI Agent 已启动 ===")
    print("输入 'quit' 退出，输入 'list' 查看会话，输入 'resume &lt;id&gt;' 恢复会话")
    
    while True:
        user_input = input("\n&gt; ").strip()
        
        if user_input.lower() == "quit":
            print("再见！")
            break
        
        if user_input.lower() == "list":
            sessions = agent.persistence.list_sessions()
            print("可用会话:")
            for sess in sessions[:5]:
                print(f"  - {sess}")
            continue
        
        if user_input.startswith("resume "):
            session_id = user_input[7:].strip()
            agent.resume_session(session_id)
            print(f"已恢复会话: {session_id}")
            continue
        
        # 运行 Agent
        try:
            response = agent.run(user_input)
            print(f"\n助手: {response}")
        except Exception as e:
            print(f"\n错误: {e}")

if __name__ == "__main__":
    main()
```

---

## 七、12 层架构实现路线图

| 层级 | 名称 | 实现优先级 | 预计工作量 |
|------|------|-----------|-----------|
| s01 | THE LOOP | 🔴 高 | 1 天 |
| s02 | TOOL DISPATCH | 🔴 高 | 2 天 |
| s03 | PLANNING | 🟡 中 | 2 天 |
| s04 | SUB-AGENTS | 🟡 中 | 3 天 |
| s05 | KNOWLEDGE ON DEMAND | 🟡 中 | 2 天 |
| s06 | CONTEXT COMPRESSION | 🔴 高 | 2 天 |
| s07 | PERSISTENT TASKS | 🟡 中 | 3 天 |
| s08 | BACKGROUND TASKS | 🟢 低 | 3 天 |
| s09 | AGENT TEAMS | 🟢 低 | 4 天 |
| s10 | TEAM PROTOCOLS | 🟢 低 | 3 天 |
| s11 | AUTONOMOUS AGENTS | 🟢 低 | 4 天 |
| s12 | WORKTREE ISOLATION | 🟢 低 | 3 天 |

### 7.1 第一阶段（MVP）- 1 周
- s01 + s02 + s06 + 基础权限
- 可投入内部使用

### 7.2 第二阶段（生产）- 2 周
- s03 + s04 + s05 + s07 + 完整权限
- 可投入生产环境

### 7.3 第三阶段（高级）- 3 周
- s08 - s12
- 完整功能集

---

## 八、关键技术选型建议

### 8.1 推荐技术栈

| 组件 | 推荐选型 | 理由 |
|------|---------|------|
| 运行时 | Node.js / Bun | TypeScript 生态完善 |
| 语言 | TypeScript | 类型安全，可维护性高 |
| API 客户端 | OpenAI SDK | 官方支持，功能完善 |
| 数据存储 | JSONL / SQLite | 简单可靠，易于调试 |
| UI | React / Ink | 跨平台，组件化 |
| 并发 | Async/Await | 现代异步模式 |

### 8.2 避免的陷阱

1. **不要过度设计** - 从 s01 开始，逐步添加
2. **不要忽略权限** - 生产系统必须有
3. **不要忘记持久化** - 用户需要恢复会话
4. **不要跳过测试** - 工具系统需要充分测试
5. **不要硬编码** - 使用配置和特性标志

---

## 九、总结

learn-coding-agent 项目提供了从简单 Agent 到生产系统的完整演进路径。关键收获：

1. **渐进式架构** - 不要一开始就追求完美
2. **工具优先** - 好的工具系统是核心
3. **用户体验** - 权限、持久化、恢复对生产至关重要
4. **可扩展性** - 设计时考虑未来演进
5. **生产就绪** - 遥测、特性标志、远程控制是企业级必需

按照本指南的步骤，您可以在 1-2 周内构建出一个可用的 Agent 系统！

---

**指南结束**

**创建人**：AI 技术学习系统  
**创建日期**：2026-04-02
