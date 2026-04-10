from typing import Optional
from .types import ErrorCode


class MCPException(Exception):
    def __init__(
        self,
        message: str,
        code: ErrorCode = ErrorCode.INTERNAL_ERROR,
        error_type: Optional[str] = None,
        details: Optional[str] = None,
        stack_trace: Optional[str] = None,
        retryable: bool = False
    ):
        self.message = message
        self.code = code
        self.error_type = error_type or self.__class__.__name__
        self.details = details
        self.stack_trace = stack_trace
        self.retryable = retryable
        super().__init__(self.message)


class SkillNotFoundException(MCPException):
    def __init__(self, skill_name: str, details: Optional[str] = None):
        super().__init__(
            message=f"Skill not found: {skill_name}",
            code=ErrorCode.NOT_FOUND,
            error_type="SkillNotFoundException",
            details=details,
            retryable=False
        )


class InvalidParameterException(MCPException):
    def __init__(self, message: str, details: Optional[str] = None):
        super().__init__(
            message=message,
            code=ErrorCode.BAD_REQUEST,
            error_type="InvalidParameterException",
            details=details,
            retryable=False
        )


class TimeoutException(MCPException):
    def __init__(self, task_id: str, timeout_ms: int, details: Optional[str] = None):
        super().__init__(
            message=f"Task {task_id} timed out after {timeout_ms}ms",
            code=ErrorCode.TIMEOUT,
            error_type="TimeoutException",
            details=details,
            retryable=True
        )


class TaskCancelledException(MCPException):
    def __init__(self, task_id: str, details: Optional[str] = None):
        super().__init__(
            message=f"Task {task_id} was cancelled",
            code=ErrorCode.BAD_REQUEST,
            error_type="TaskCancelledException",
            details=details,
            retryable=False
        )


class ConcurrentExecutionException(MCPException):
    def __init__(self, task_id: str, details: Optional[str] = None):
        super().__init__(
            message=f"Task {task_id} is already executing",
            code=ErrorCode.BAD_REQUEST,
            error_type="ConcurrentExecutionException",
            details=details,
            retryable=False
        )
