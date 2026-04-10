import time
from datetime import datetime
from typing import Dict, List, Optional, Any, Callable, Tuple
from .types import (
    MCPSkillRequest,
    MCPSuccessResponse,
    MCPFailureResponse,
    SkillExecutionData,
    ResponseMetadata,
    ErrorInfo,
    ErrorMetadata,
    Task,
    TaskType,
    Priority,
    TaskStatus,
    SkillDefinition,
    ErrorCode,
)
from .exceptions import (
    MCPException,
    SkillNotFoundException,
    InvalidParameterException,
    TimeoutException,
)
from .skill_registry import SkillRegistry
from .skill_matcher import SkillMatcher
from .task_manager import TaskManager
from .retry import RetryStrategy, get_default_retry_strategy, capture_stack_trace


class MCP:
    def __init__(
        self,
        skill_registry: Optional[SkillRegistry] = None,
        task_manager: Optional[TaskManager] = None,
        default_timeout_ms: int = 500,
    ):
        self.skill_registry = skill_registry or SkillRegistry()
        self.task_manager = task_manager or TaskManager()
        self.default_timeout_ms = default_timeout_ms
        self.skill_matcher = SkillMatcher(self.skill_registry)
        self._start_time = datetime.now()

    def register_skill(
        self,
        name: str,
        description: str,
        priority: Priority,
        category: str,
        handler: Callable,
        enabled: bool = True
    ) -> SkillDefinition:
        return self.skill_registry.register(
            name, description, priority, category, handler, enabled
        )

    def execute(
        self,
        request: MCPSkillRequest
    ) -> MCPSuccessResponse | MCPFailureResponse:
        start_time = time.time()
        task = self._create_task_from_request(request)

        try:
            self.task_manager.start_task(task.taskId)
            result = self._execute_skills(task, request, start_time)
            total_execution_time = int((time.time() - start_time) * 1000)

            self.task_manager.complete_task(
                task.taskId,
                result={"skills": result},
                message="All skills executed successfully"
            )

            first_skill_result = result[0] if result else None
            execution_data = SkillExecutionData(
                skill=first_skill_result["skill"] if first_skill_result else "",
                result=first_skill_result["result"] if first_skill_result else None,
                executionTime=first_skill_result["executionTime"] if first_skill_result else 0,
                metadata={}
            )

            return MCPSuccessResponse(
                taskId=task.taskId,
                status="SUCCESS",
                code=200,
                message="Execution completed successfully",
                data=execution_data,
                metadata=ResponseMetadata(
                    timestamp=datetime.now(),
                    executionTime=total_execution_time
                )
            )

        except MCPException as e:
            total_execution_time = int((time.time() - start_time) * 1000)
            error_info = ErrorInfo(
                type=e.error_type,
                message=e.message,
                details=e.details or "",
                stackTrace=e.stack_trace or capture_stack_trace()
            )

            self.task_manager.fail_task(
                task.taskId,
                error_info,
                e.retryable,
                e.message
            )

            return MCPFailureResponse(
                taskId=task.taskId,
                status="FAILED",
                code=e.code.value,
                message=e.message,
                error=error_info,
                metadata=ErrorMetadata(
                    timestamp=datetime.now(),
                    retryable=e.retryable,
                    executionTime=total_execution_time
                )
            )

        except Exception as e:
            total_execution_time = int((time.time() - start_time) * 1000)
            error_info = ErrorInfo(
                type=type(e).__name__,
                message=str(e),
                details="Unexpected error occurred during execution",
                stackTrace=capture_stack_trace()
            )

            self.task_manager.fail_task(
                task.taskId,
                error_info,
                True,
                "Unexpected internal error"
            )

            return MCPFailureResponse(
                taskId=task.taskId,
                status="FAILED",
                code=500,
                message="Unexpected internal error",
                error=error_info,
                metadata=ErrorMetadata(
                    timestamp=datetime.now(),
                    retryable=True,
                    executionTime=total_execution_time
                )
            )

    def execute_skill(
        self,
        skill_name: str,
        parameters: Dict[str, Any],
        timeout_ms: Optional[int] = None
    ) -> Tuple[Any, Optional[ErrorInfo]]:
        timeout = timeout_ms or self.default_timeout_ms
        start_time = time.time()

        try:
            if not self.skill_registry.is_enabled(skill_name):
                raise SkillNotFoundException(skill_name)

            result = self.skill_registry.invoke_skill(skill_name, parameters)

            elapsed = int((time.time() - start_time) * 1000)
            if elapsed > timeout:
                raise TimeoutException(
                    task_id="",
                    timeout_ms=timeout,
                    details=f"Skill {skill_name} exceeded timeout of {timeout}ms"
                )

            return result, None

        except SkillNotFoundException as e:
            return None, ErrorInfo(
                type=e.error_type,
                message=e.message,
                details=e.details or e.message,
                stackTrace=capture_stack_trace()
            )

        except Exception as e:
            return None, ErrorInfo(
                type=type(e).__name__,
                message=str(e),
                details="Error executing skill",
                stackTrace=capture_stack_trace()
            )

    def _create_task_from_request(self, request: MCPSkillRequest) -> Task:
        skill_names = [s.name for s in request.skills]
        task_name = f"Execute {len(skill_names)} skill(s): {', '.join(skill_names[:3])}"

        return self.task_manager.create_task(
            task_name=task_name,
            task_type=request.taskType,
            priority=request.priority,
            skill_names=skill_names,
            created_by=request.metadata.source if request.metadata else "expert_group",
            metadata={
                "executionMode": request.executionMode,
                "callbackUrl": request.callbackUrl,
                "requestMetadata": request.metadata.dict() if request.metadata else {},
            }
        )

    def _execute_skills(
        self,
        task: Task,
        request: MCPSkillRequest,
        start_time: float
    ) -> List[Dict[str, Any]]:
        results = []

        for idx, (skill_invocation, task_skill) in enumerate(zip(request.skills, task.skills)):
            skill_name = skill_invocation.name
            parameters = skill_invocation.parameters

            self.task_manager.update_skill_status(
                task, idx, "RUNNING"
            )

            execution_start = time.time()
            result, error = self.execute_skill(
                skill_name,
                parameters,
                self.default_timeout_ms
            )
            execution_time = int((time.time() - execution_start) * 1000)

            if error:
                self.task_manager.update_skill_status(
                    task, idx, "FAILED", result, error
                )
                raise MCPException(
                    message=f"Skill {skill_name} failed: {error.details}",
                    code=ErrorCode.NOT_FOUND if error.type == "SkillNotFoundException" else ErrorCode.INTERNAL_ERROR,
                    error_type=error.type,
                    details=error.details,
                    stack_trace=error.stackTrace,
                    retryable=error.type != "SkillNotFoundException"
                )

            self.task_manager.update_skill_status(
                task, idx, "COMPLETED", result
            )

            progress = ((idx + 1) / len(request.skills)) * 100
            self.task_manager.update_progress(
                task.taskId,
                progress,
                f"Completed {skill_name}",
                skill_name,
                int((time.time() - start_time) * 1000)
            )

            results.append({
                "skill": skill_name,
                "result": result,
                "executionTime": execution_time
            })

        return results

    def execute_with_retry(
        self,
        request: MCPSkillRequest,
        retry_strategy: Optional[RetryStrategy] = None
    ) -> MCPSuccessResponse | MCPFailureResponse:
        if retry_strategy is None:
            retry_strategy = RetryStrategy(max_retries=3)

        retry_count = 0
        last_response = None

        while retry_count <= retry_strategy.max_retries:
            response = self.execute(request)

            if response.code == 200:
                return response

            last_response = response

            if response.metadata.retryable:
                delay = retry_strategy.get_delay(retry_count)
                time.sleep(delay / 1000.0)
                retry_count += 1
            else:
                break

        return last_response

    def get_task(self, task_id: str) -> Optional[Task]:
        return self.task_manager.get_task(task_id)

    def list_tasks(
        self,
        status: Optional[TaskStatus] = None,
        limit: int = 100
    ) -> List[Task]:
        return self.task_manager.list_tasks(status, limit)

    def cancel_task(self, task_id: str, reason: str = "Task cancelled by user") -> None:
        self.task_manager.cancel_task(task_id, reason)

    def get_statistics(self) -> Dict[str, Any]:
        return {
            "uptime_seconds": (datetime.now() - self._start_time).total_seconds(),
            "skills_total": len(self.skill_registry.list_skills()),
            "skills_enabled": len([s for s in self.skill_registry.list_skills() if s.enabled]),
            "task_statistics": self.task_manager.get_statistics()
        }

    def list_available_skills(self) -> List[SkillDefinition]:
        return self.skill_registry.list_skills()

    def match_skills(
        self,
        task_type: TaskType,
        required_skills: Optional[List[str]] = None
    ) -> List[SkillDefinition]:
        return self.skill_matcher.match_skills(task_type, required_skills)
