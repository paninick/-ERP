import json
from datetime import datetime
from typing import Dict, Optional, Any, List
from .types import LogLevel, LogEntry
from .task_manager import TaskManager


class StructuredLogger:
    def __init__(self, task_manager: TaskManager):
        self.task_manager = task_manager

    def debug(
        self,
        message: str,
        task_id: Optional[str] = None,
        skill_id: Optional[str] = None,
        **kwargs
    ) -> LogEntry:
        return self.task_manager.log(LogLevel.DEBUG, task_id, skill_id, message, kwargs)

    def info(
        self,
        message: str,
        task_id: Optional[str] = None,
        skill_id: Optional[str] = None,
        **kwargs
    ) -> LogEntry:
        return self.task_manager.log(LogLevel.INFO, task_id, skill_id, message, kwargs)

    def warning(
        self,
        message: str,
        task_id: Optional[str] = None,
        skill_id: Optional[str] = None,
        **kwargs
    ) -> LogEntry:
        return self.task_manager.log(LogLevel.WARNING, task_id, skill_id, message, kwargs)

    def error(
        self,
        message: str,
        task_id: Optional[str] = None,
        skill_id: Optional[str] = None,
        **kwargs
    ) -> LogEntry:
        return self.task_manager.log(LogLevel.ERROR, task_id, skill_id, message, kwargs)

    def get_logs(
        self,
        task_id: Optional[str] = None,
        level: Optional[LogLevel] = None,
        limit: int = 100
    ) -> List[LogEntry]:
        return self.task_manager.get_logs(task_id, level, limit)

    def export_logs_as_json(
        self,
        task_id: Optional[str] = None,
        limit: int = 1000
    ) -> str:
        logs = self.get_logs(task_id, limit=limit)
        return json.dumps([log.dict() for log in logs], indent=2, default=str)
