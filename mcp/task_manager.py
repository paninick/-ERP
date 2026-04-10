import uuid
from datetime import datetime
from typing import Dict, List, Optional, Any
from collections import OrderedDict
from .types import (
    Task,
    TaskType,
    Priority,
    TaskStatus,
    SkillResult,
    SkillStatus,
    ErrorInfo,
    ProgressFeedback,
    CompletionFeedback,
    FailureFeedback,
    Alert,
    AlertLevel,
    LogEntry,
    LogLevel,
)
from .exceptions import ConcurrentExecutionException, TaskCancelledException


class TaskManager:
    def __init__(self, max_history: int = 1000):
        self._tasks: OrderedDict[str, Task] = OrderedDict()
        self._logs: List[LogEntry] = []
        self._alerts: List[Alert] = []
        self._max_history = max_history

    def generate_task_id(self) -> str:
        date_str = datetime.now().strftime("%Y%m%d")
        return f"TASK-{date_str}-{uuid.uuid4().hex[:4]}"

    def create_task(
        self,
        task_name: str,
        task_type: TaskType,
        priority: Priority,
        skill_names: List[str],
        created_by: str = "expert_group",
        estimated_duration: Optional[int] = None,
        metadata: Optional[Dict[str, Any]] = None
    ) -> Task:
        task_id = self.generate_task_id()
        now = datetime.now()

        skills: List[SkillResult] = []
        for idx, skill_name in enumerate(skill_names):
            skill_id = f"{task_id}-skill-{idx+1:03d}"
            skills.append(SkillResult(
                skillId=skill_id,
                skillName=skill_name,
                parameters={},
                status=SkillStatus.PENDING
            ))

        task = Task(
            taskId=task_id,
            taskName=task_name,
            taskType=task_type,
            priority=priority,
            status=TaskStatus.PENDING,
            progress=0.0,
            skills=skills,
            createdAt=now,
            createdBy=created_by,
            estimatedDuration=estimated_duration,
            metadata=metadata or {}
        )

        self._tasks[task_id] = task
        self._trim_history()
        self.log(LogLevel.INFO, task_id, None, f"Task created: {task_name}")

        return task

    def get_task(self, task_id: str) -> Optional[Task]:
        return self._tasks.get(task_id)

    def list_tasks(
        self,
        status: Optional[TaskStatus] = None,
        limit: int = 100
    ) -> List[Task]:
        tasks = list(self._tasks.values())
        if status:
            tasks = [t for t in tasks if t.status == status]
        tasks.reverse()
        return tasks[:limit]

    def start_task(self, task_id: str) -> Task:
        task = self.get_task(task_id)
        if task is None:
            raise ValueError(f"Task not found: {task_id}")

        if task.status == TaskStatus.RUNNING:
            raise ConcurrentExecutionException(task_id)

        if task.status in [TaskStatus.COMPLETED, TaskStatus.FAILED, TaskStatus.CANCELLED]:
            raise ValueError(f"Cannot start task in {task.status} status")

        task.status = TaskStatus.RUNNING
        task.startedAt = datetime.now()
        task.progress = 0.0
        self.log(LogLevel.INFO, task_id, None, f"Task started: {task.taskName}")

        return task

    def update_progress(
        self,
        task_id: str,
        progress: float,
        message: str,
        current_skill: Optional[str] = None,
        estimated_remaining: int = 0
    ) -> ProgressFeedback:
        task = self.get_task(task_id)
        if task:
            task.progress = progress

        feedback = ProgressFeedback(
            taskId=task_id,
            status=TaskStatus.RUNNING,
            progress=progress,
            message=message,
            currentSkill=current_skill,
            estimatedRemaining=estimated_remaining,
            timestamp=datetime.now()
        )

        return feedback

    def complete_task(
        self,
        task_id: str,
        result: Optional[Dict[str, Any]] = None,
        message: str = "Task completed successfully"
    ) -> CompletionFeedback:
        task = self.get_task(task_id)
        if task is None:
            raise ValueError(f"Task not found: {task_id}")

        task.status = TaskStatus.COMPLETED
        task.progress = 100.0
        task.completedAt = datetime.now()
        if task.startedAt:
            task.actualDuration = int((task.completedAt - task.startedAt).total_seconds() * 1000)
        task.result = result

        self.log(LogLevel.INFO, task_id, None, message)

        feedback = CompletionFeedback(
            taskId=task_id,
            status=TaskStatus.COMPLETED,
            progress=100.0,
            message=message,
            result=result,
            actualDuration=task.actualDuration or 0,
            timestamp=datetime.now()
        )

        return feedback

    def fail_task(
        self,
        task_id: str,
        error: ErrorInfo,
        retryable: bool = False,
        message: str = "Task failed"
    ) -> FailureFeedback:
        task = self.get_task(task_id)
        if task is None:
            raise ValueError(f"Task not found: {task_id}")

        task.status = TaskStatus.FAILED
        task.error = error
        task.completedAt = datetime.now()
        if task.startedAt:
            task.actualDuration = int((task.completedAt - task.startedAt).total_seconds() * 1000)

        self.log(LogLevel.ERROR, task_id, None, message, {"error": error})
        self.alert(
            task_id=task_id,
            level=AlertLevel.ERROR,
            message=message,
            details=error.details,
            action_required=retryable
        )

        feedback = FailureFeedback(
            taskId=task_id,
            status=TaskStatus.FAILED,
            progress=task.progress,
            message=message,
            error=error,
            retryable=retryable,
            actualDuration=task.actualDuration or 0,
            timestamp=datetime.now()
        )

        return feedback

    def cancel_task(self, task_id: str, reason: str = "Task cancelled by user") -> None:
        task = self.get_task(task_id)
        if task is None:
            raise ValueError(f"Task not found: {task_id}")

        if task.status not in [TaskStatus.PENDING, TaskStatus.RUNNING, TaskStatus.PAUSED]:
            raise TaskCancelledException(task_id, f"Cannot cancel task in {task.status} status")

        task.status = TaskStatus.CANCELLED
        task.completedAt = datetime.now()
        self.log(LogLevel.INFO, task_id, None, reason)

    def pause_task(self, task_id: str) -> None:
        task = self.get_task(task_id)
        if task is None:
            raise ValueError(f"Task not found: {task_id}")

        if task.status != TaskStatus.RUNNING:
            raise ValueError(f"Cannot pause task in {task.status} status")

        task.status = TaskStatus.PAUSED
        self.log(LogLevel.INFO, task_id, None, "Task paused")

    def resume_task(self, task_id: str) -> None:
        task = self.get_task(task_id)
        if task is None:
            raise ValueError(f"Task not found: {task_id}")

        if task.status != TaskStatus.PAUSED:
            raise ValueError(f"Cannot resume task in {task.status} status")

        task.status = TaskStatus.RUNNING
        self.log(LogLevel.INFO, task_id, None, "Task resumed")

    def update_skill_status(
        self,
        task: Task,
        skill_index: int,
        status: SkillStatus,
        result: Optional[Any] = None,
        error: Optional[ErrorInfo] = None
    ) -> SkillResult:
        if 0 <= skill_index < len(task.skills):
            skill = task.skills[skill_index]
            skill.status = status
            if result is not None:
                skill.result = result
            if error is not None:
                skill.error = error
            return skill
        raise IndexError(f"Skill index {skill_index} out of range for task {task.taskId}")

    def log(
        self,
        level: LogLevel,
        task_id: Optional[str],
        skill_id: Optional[str],
        message: str,
        details: Optional[Dict[str, Any]] = None
    ) -> LogEntry:
        entry = LogEntry(
            timestamp=datetime.now(),
            level=level,
            taskId=task_id,
            skillId=skill_id,
            message=message,
            details=details or {}
        )
        self._logs.append(entry)
        if len(self._logs) > self._max_history * 2:
            self._logs = self._logs[-self._max_history:]
        return entry

    def get_logs(
        self,
        task_id: Optional[str] = None,
        level: Optional[LogLevel] = None,
        limit: int = 100
    ) -> List[LogEntry]:
        logs = self._logs
        if task_id:
            logs = [l for l in logs if l.taskId == task_id]
        if level:
            logs = [l for l in logs if l.level == level]
        logs.reverse()
        return logs[:limit]

    def alert(
        self,
        task_id: str,
        level: AlertLevel,
        message: str,
        details: str,
        action_required: bool = False
    ) -> Alert:
        alert = Alert(
            taskId=task_id,
            level=level,
            message=message,
            details=details,
            actionRequired=action_required,
            timestamp=datetime.now()
        )
        self._alerts.append(alert)
        if len(self._alerts) > 100:
            self._alerts = self._alerts[-100:]
        return alert

    def get_alerts(
        self,
        level: Optional[AlertLevel] = None,
        unread_only: bool = False
    ) -> List[Alert]:
        alerts = self._alerts
        if level:
            alerts = [a for a in alerts if a.level == level]
        alerts.reverse()
        return alerts

    def get_statistics(self) -> Dict[str, Any]:
        all_tasks = list(self._tasks.values())
        completed = [t for t in all_tasks if t.status == TaskStatus.COMPLETED]
        failed = [t for t in all_tasks if t.status == TaskStatus.FAILED]
        pending = [t for t in all_tasks if t.status == TaskStatus.PENDING]
        running = [t for t in all_tasks if t.status == TaskStatus.RUNNING]

        success_rate = 0.0
        if completed or failed:
            success_rate = len(completed) / (len(completed) + len(failed)) * 100

        total_duration = sum(t.actualDuration for t in completed if t.actualDuration)
        avg_duration = total_duration / len(completed) if completed else 0

        return {
            "total_tasks": len(all_tasks),
            "completed": len(completed),
            "failed": len(failed),
            "pending": len(pending),
            "running": len(running),
            "success_rate": round(success_rate, 2),
            "average_duration_ms": round(avg_duration, 2)
        }

    def _trim_history(self) -> None:
        if len(self._tasks) > self._max_history:
            keys = list(self._tasks.keys())[:-self._max_history]
            for key in keys:
                del self._tasks[key]
