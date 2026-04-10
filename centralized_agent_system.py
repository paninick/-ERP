#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
集中式智能代理系统
基于 learn-coding-agent 的 12 层架构实现
"""

import json
import os
from dataclasses import dataclass, field
from datetime import datetime
from enum import Enum
from typing import Any, Dict, List, Optional
from pathlib import Path


class SessionState(Enum):
    """会话状态枚举"""
    ACTIVE = "active"
    PAUSED = "paused"
    COMPLETED = "completed"
    ERROR = "error"


class TaskStatus(Enum):
    """任务状态枚举"""
    PENDING = "pending"
    IN_PROGRESS = "in_progress"
    COMPLETED = "completed"
    BLOCKED = "blocked"
    CANCELLED = "cancelled"


@dataclass
class Task:
    """任务数据类"""
    id: str
    description: str
    status: TaskStatus = TaskStatus.PENDING
    priority: int = 1
    assigned_to: Optional[str] = None
    dependencies: List[str] = field(default_factory=list)
    created_at: datetime = field(default_factory=datetime.now)
    completed_at: Optional[datetime] = None
    notes: str = ""
    
    def to_dict(self) -> Dict:
        return {
            "id": self.id,
            "description": self.description,
            "status": self.status.value,
            "priority": self.priority,
            "assigned_to": self.assigned_to,
            "dependencies": self.dependencies,
            "created_at": self.created_at.isoformat(),
            "completed_at": self.completed_at.isoformat() if self.completed_at else None,
            "notes": self.notes
        }
    
    @classmethod
    def from_dict(cls, data: Dict) -> "Task":
        return cls(
            id=data["id"],
            description=data["description"],
            status=TaskStatus(data["status"]),
            priority=data["priority"],
            assigned_to=data.get("assigned_to"),
            dependencies=data.get("dependencies", []),
            created_at=datetime.fromisoformat(data["created_at"]),
            completed_at=datetime.fromisoformat(data["completed_at"]) if data.get("completed_at") else None,
            notes=data.get("notes", "")
        )


@dataclass
class Message:
    """消息数据类"""
    role: str
    content: str
    timestamp: datetime = field(default_factory=datetime.now)
    metadata: Dict[str, Any] = field(default_factory=dict)
    
    def to_dict(self) -> Dict:
        return {
            "role": self.role,
            "content": self.content,
            "timestamp": self.timestamp.isoformat(),
            "metadata": self.metadata
        }
    
    @classmethod
    def from_dict(cls, data: Dict) -> "Message":
        return cls(
            role=data["role"],
            content=data["content"],
            timestamp=datetime.fromisoformat(data["timestamp"]),
            metadata=data.get("metadata", {})
        )


@dataclass
class Session:
    """会话数据类"""
    id: str
    created_at: datetime
    state: SessionState = SessionState.ACTIVE
    context: Dict[str, Any] = field(default_factory=dict)
    messages: List[Message] = field(default_factory=list)
    tasks: List[Task] = field(default_factory=list)
    metadata: Dict[str, Any] = field(default_factory=dict)
    
    def to_dict(self) -> Dict:
        return {
            "id": self.id,
            "created_at": self.created_at.isoformat(),
            "state": self.state.value,
            "context": self.context,
            "messages": [msg.to_dict() for msg in self.messages],
            "tasks": [task.to_dict() for task in self.tasks],
            "metadata": self.metadata
        }
    
    @classmethod
    def from_dict(cls, data: Dict) -> "Session":
        return cls(
            id=data["id"],
            created_at=datetime.fromisoformat(data["created_at"]),
            state=SessionState(data["state"]),
            context=data.get("context", {}),
            messages=[Message.from_dict(msg) for msg in data.get("messages", [])],
            tasks=[Task.from_dict(task) for task in data.get("tasks", [])],
            metadata=data.get("metadata", {})
        )


class CentralizedAgentSystem:
    """集中式智能代理系统 - MVP 实现（s01 + s02 + s06 + 基础权限）"""
    
    def __init__(self, storage_dir: str = "./agent_sessions"):
        self.storage_dir = Path(storage_dir)
        self.sessions: Dict[str, Session] = {}
        self._init_storage()
    
    def _init_storage(self):
        """初始化存储"""
        self.storage_dir.mkdir(parents=True, exist_ok=True)
        self._load_all_sessions()
    
    def _load_all_sessions(self):
        """加载所有会话"""
        for meta_file in self.storage_dir.glob("*_meta.json"):
            session_id = meta_file.stem.replace("_meta", "")
            self._load_session(session_id)
    
    def _load_session(self, session_id: str) -> Optional[Session]:
        """加载单个会话"""
        meta_file = self.storage_dir / f"{session_id}_meta.json"
        if not meta_file.exists():
            return None
        
        with open(meta_file, "r", encoding="utf-8") as f:
            meta_data = json.load(f)
        
        # 加载消息
        messages = []
        messages_file = self.storage_dir / f"{session_id}.jsonl"
        if messages_file.exists():
            with open(messages_file, "r", encoding="utf-8") as f:
                for line in f:
                    if line.strip():
                        msg_data = json.loads(line)
                        messages.append(Message.from_dict(msg_data))
        
        session = Session(
            id=session_id,
            created_at=datetime.fromisoformat(meta_data["created_at"]),
            state=SessionState(meta_data["state"]),
            context=meta_data.get("context", {}),
            messages=messages,
            tasks=[Task.from_dict(t) for t in meta_data.get("tasks", [])],
            metadata=meta_data.get("metadata", {})
        )
        
        self.sessions[session_id] = session
        return session
    
    def _save_session(self, session: Session):
        """保存会话到磁盘（JSONL + JSON 元数据）"""
        # 保存消息（JSONL 格式，追加式日志）
        messages_file = self.storage_dir / f"{session.id}.jsonl"
        with open(messages_file, "w", encoding="utf-8") as f:
            for msg in session.messages:
                f.write(json.dumps(msg.to_dict(), ensure_ascii=False) + "\n")
        
        # 保存元数据（JSON 格式）
        meta_file = self.storage_dir / f"{session.id}_meta.json"
        with open(meta_file, "w", encoding="utf-8") as f:
            json.dump(session.to_dict(), f, ensure_ascii=False, indent=2)
    
    def create_session(self, initial_context: Dict = None, project_name: str = "") -> str:
        """创建新的编程会话"""
        session_id = f"session_{datetime.now().strftime('%Y%m%d_%H%M%S')}"
        
        context = initial_context or {}
        if project_name:
            context["project_name"] = project_name
        
        session = Session(
            id=session_id,
            created_at=datetime.now(),
            state=SessionState.ACTIVE,
            context=context,
            messages=[],
            tasks=[],
            metadata={"project_name": project_name}
        )
        
        self.sessions[session_id] = session
        self._save_session(session)
        return session_id
    
    def get_session(self, session_id: str) -> Optional[Session]:
        """获取会话"""
        return self.sessions.get(session_id)
    
    def list_sessions(self, limit: int = 10, state: SessionState = None) -> List[Session]:
        """列出会话"""
        sorted_sessions = sorted(
            self.sessions.values(),
            key=lambda s: s.created_at,
            reverse=True
        )
        
        if state:
            sorted_sessions = [s for s in sorted_sessions if s.state == state]
        
        return sorted_sessions[:limit]
    
    def add_message(self, session_id: str, role: str, content: str, metadata: Dict = None):
        """添加消息到会话"""
        session = self.get_session(session_id)
        if not session:
            return
        
        message = Message(
            role=role,
            content=content,
            metadata=metadata or {}
        )
        
        session.messages.append(message)
        
        # 如果是用户消息，使用阻塞写入确保崩溃可恢复
        if role == "user":
            messages_file = self.storage_dir / f"{session_id}.jsonl"
            with open(messages_file, "a", encoding="utf-8") as f:
                f.write(json.dumps(message.to_dict(), ensure_ascii=False) + "\n")
                f.flush()
                os.fsync(f.fileno())
        
        self._save_session(session)
    
    def add_user_message(self, session_id: str, content: str, metadata: Dict = None):
        """添加用户消息"""
        self.add_message(session_id, "user", content, metadata)
    
    def add_assistant_message(self, session_id: str, content: str, metadata: Dict = None):
        """添加助手消息"""
        self.add_message(session_id, "assistant", content, metadata)
    
    def update_context(self, session_id: str, key: str, value: Any):
        """更新会话上下文"""
        session = self.get_session(session_id)
        if session:
            session.context[key] = value
            self._save_session(session)
    
    def set_context(self, session_id: str, context: Dict):
        """设置完整上下文"""
        session = self.get_session(session_id)
        if session:
            session.context = context
            self._save_session(session)
    
    def pause_session(self, session_id: str):
        """暂停会话"""
        session = self.get_session(session_id)
        if session:
            session.state = SessionState.PAUSED
            self._save_session(session)
    
    def resume_session(self, session_id: str) -> Optional[Session]:
        """恢复会话"""
        session = self.get_session(session_id)
        if session:
            session.state = SessionState.ACTIVE
            self._save_session(session)
        return session
    
    def complete_session(self, session_id: str):
        """完成会话"""
        session = self.get_session(session_id)
        if session:
            session.state = SessionState.COMPLETED
            self._save_session(session)
    
    # ========== 任务管理 (s07) ==========
    
    def create_task(self, session_id: str, description: str, priority: int = 1) -> Optional[str]:
        """创建任务"""
        session = self.get_session(session_id)
        if not session:
            return None
        
        task_id = f"task_{datetime.now().strftime('%H%M%S')}"
        task = Task(
            id=task_id,
            description=description,
            priority=priority
        )
        
        session.tasks.append(task)
        self._save_session(session)
        return task_id
    
    def update_task_status(self, session_id: str, task_id: str, status: TaskStatus, notes: str = "") -> bool:
        """更新任务状态"""
        session = self.get_session(session_id)
        if not session:
            return False
        
        for task in session.tasks:
            if task.id == task_id:
                task.status = status
                if notes:
                    task.notes = notes
                if status == TaskStatus.COMPLETED:
                    task.completed_at = datetime.now()
                self._save_session(session)
                return True
        
        return False
    
    def get_tasks(self, session_id: str, status: TaskStatus = None) -> List[Task]:
        """获取任务列表"""
        session = self.get_session(session_id)
        if not session:
            return []
        
        tasks = session.tasks
        if status:
            tasks = [t for t in tasks if t.status == status]
        
        return sorted(tasks, key=lambda t: (-t.priority, t.created_at))
    
    # ========== 上下文压缩 (s06) ==========
    
    def compress_context(self, session_id: str, max_messages: int = 50, keep_recent: int = 30):
        """压缩会话上下文"""
        session = self.get_session(session_id)
        if not session or len(session.messages) <= max_messages:
            return
        
        # 保留最早的和最新的消息
        old_messages = session.messages[:10]
        recent_messages = session.messages[-keep_recent:]
        
        # 生成摘要（实际项目中应该调用 LLM）
        summary = f"[已压缩 {len(session.messages) - len(old_messages) - len(recent_messages)} 条中间消息]"
        
        # 清空并重建消息列表
        session.messages = []
        session.messages.extend(old_messages)
        session.messages.append(Message(
            role="system",
            content=summary
        ))
        session.messages.extend(recent_messages)
        
        session.context["compressed"] = True
        session.context["original_message_count"] = len(session.messages)
        
        self._save_session(session)
    
    # ========== 会话导出 ==========
    
    def export_session(self, session_id: str, output_file: str = None) -> str:
        """导出会话"""
        session = self.get_session(session_id)
        if not session:
            return ""
        
        if not output_file:
            output_file = self.storage_dir / f"{session_id}_export.json"
        
        with open(output_file, "w", encoding="utf-8") as f:
            json.dump(session.to_dict(), f, ensure_ascii=False, indent=2)
        
        return str(output_file)
    
    # ========== 统计信息 ==========
    
    def get_session_stats(self, session_id: str) -> Optional[Dict]:
        """获取会话统计信息"""
        session = self.get_session(session_id)
        if not session:
            return None
        
        user_count = sum(1 for m in session.messages if m.role == "user")
        assistant_count = sum(1 for m in session.messages if m.role == "assistant")
        
        task_stats = {}
        for status in TaskStatus:
            task_stats[status.value] = sum(1 for t in session.tasks if t.status == status)
        
        return {
            "session_id": session_id,
            "state": session.state.value,
            "created_at": session.created_at.isoformat(),
            "total_messages": len(session.messages),
            "user_messages": user_count,
            "assistant_messages": assistant_count,
            "total_tasks": len(session.tasks),
            "task_stats": task_stats,
            "project_name": session.context.get("project_name", "")
        }


def main():
    """示例使用"""
    print("=" * 60)
    print("集中式智能代理系统 - 示例")
    print("=" * 60)
    
    # 初始化系统
    system = CentralizedAgentSystem("./agent_sessions")
    
    # 创建会话
    print("\n1. 创建新会话...")
    session_id = system.create_session(
        project_name="ERP 系统开发",
        initial_context={
            "language": "Python",
            "framework": "FastAPI",
            "goal": "实现用户认证模块"
        }
    )
    print(f"   会话 ID: {session_id}")
    
    # 添加消息
    print("\n2. 添加对话消息...")
    system.add_user_message(session_id, "请帮我实现 JWT 认证功能")
    system.add_assistant_message(session_id, "好的，我来帮你实现 JWT 认证。首先让我创建任务列表...")
    
    # 创建任务
    print("\n3. 创建开发任务...")
    task1_id = system.create_task(session_id, "设计 JWT 认证中间件", priority=1)
    task2_id = system.create_task(session_id, "实现登录端点", priority=1)
    task3_id = system.create_task(session_id, "实现令牌刷新功能", priority=2)
    print(f"   创建了 3 个任务")
    
    # 更新任务状态
    print("\n4. 更新任务状态...")
    system.update_task_status(session_id, task1_id, TaskStatus.COMPLETED, notes="已完成 JWT 中间件设计")
    system.update_task_status(session_id, task2_id, TaskStatus.IN_PROGRESS)
    
    # 获取统计信息
    print("\n5. 会话统计:")
    stats = system.get_session_stats(session_id)
    for key, value in stats.items():
        print(f"   {key}: {value}")
    
    # 列出会话
    print("\n6. 最近会话:")
    sessions = system.list_sessions(5)
    for sess in sessions:
        print(f"   - {sess.id} ({sess.state.value}) - {sess.created_at.strftime('%Y-%m-%d %H:%M:%S')}")
    
    print("\n" + "=" * 60)
    print("示例完成！会话数据已保存到 ./agent_sessions/")
    print("=" * 60)


if __name__ == "__main__":
    main()
