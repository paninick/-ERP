from enum import Enum
from typing import Dict, List, Optional, Any, Union
from datetime import datetime
from pydantic import BaseModel, Field


class TaskType(str, Enum):
    SEARCH = "SEARCH"
    FILE_OPERATION = "FILE_OPERATION"
    PROJECT_MANAGEMENT = "PROJECT_MANAGEMENT"
    SYSTEM_TOOL = "SYSTEM_TOOL"
    MCP_TOOL = "MCP_TOOL"
    DIAGNOSTIC = "DIAGNOSTIC"
    WEB = "WEB"


class Priority(str, Enum):
    P0 = "P0"
    P1 = "P1"
    P2 = "P2"


class TaskStatus(str, Enum):
    PENDING = "PENDING"
    RUNNING = "RUNNING"
    PAUSED = "PAUSED"
    COMPLETED = "COMPLETED"
    FAILED = "FAILED"
    CANCELLED = "CANCELLED"


class SkillStatus(str, Enum):
    PENDING = "PENDING"
    RUNNING = "RUNNING"
    COMPLETED = "COMPLETED"
    FAILED = "FAILED"


class LogLevel(str, Enum):
    DEBUG = "DEBUG"
    INFO = "INFO"
    WARNING = "WARNING"
    ERROR = "ERROR"


class AlertLevel(str, Enum):
    ERROR = "ERROR"
    WARNING = "WARNING"
    INFO = "INFO"


class ErrorCode(int, Enum):
    SUCCESS = 200
    BAD_REQUEST = 400
    NOT_FOUND = 404
    TIMEOUT = 408
    TOO_MANY_REQUESTS = 429
    INTERNAL_ERROR = 500


class SkillInvocation(BaseModel):
    name: str
    parameters: Dict[str, Any] = Field(default_factory=dict)


class Metadata(BaseModel):
    source: Optional[str] = None
    timestamp: Optional[datetime] = None
    retryCount: int = 0


class SkillExecutionData(BaseModel):
    skill: str
    result: Any
    executionTime: int = Field(ge=0)
    metadata: Dict[str, Any] = Field(default_factory=dict)


class ErrorInfo(BaseModel):
    type: str
    message: str
    details: str
    stackTrace: Optional[str] = None


class ErrorMetadata(BaseModel):
    timestamp: datetime
    retryable: bool = False
    executionTime: Optional[int] = None


class ResponseMetadata(BaseModel):
    timestamp: datetime
    executionTime: int = Field(ge=0)


class MCPSkillRequest(BaseModel):
    taskId: str
    taskType: TaskType
    priority: Priority
    skills: List[SkillInvocation]
    executionMode: str = "SYNC"
    callbackUrl: Optional[str] = None
    metadata: Optional[Metadata] = None


class MCPSuccessResponse(BaseModel):
    taskId: str
    status: str = "SUCCESS"
    code: int = 200
    message: str
    data: SkillExecutionData
    metadata: ResponseMetadata


class MCPFailureResponse(BaseModel):
    taskId: str
    status: str = "FAILED"
    code: int
    message: str
    error: Optional[ErrorInfo] = None
    metadata: ErrorMetadata


class SkillResult(BaseModel):
    skillId: str
    skillName: str
    parameters: Dict[str, Any]
    status: SkillStatus
    result: Optional[Any] = None
    error: Optional[ErrorInfo] = None


class Task(BaseModel):
    taskId: str
    taskName: str
    taskType: TaskType
    priority: Priority
    status: TaskStatus
    progress: float = Field(ge=0, le=100)
    skills: List[SkillResult]
    createdAt: datetime
    startedAt: Optional[datetime] = None
    completedAt: Optional[datetime] = None
    estimatedDuration: Optional[int] = None
    actualDuration: Optional[int] = None
    createdBy: str
    metadata: Dict[str, Any] = Field(default_factory=dict)
    result: Optional[Dict[str, Any]] = None
    error: Optional[ErrorInfo] = None


class ProgressFeedback(BaseModel):
    taskId: str
    status: TaskStatus
    progress: float
    message: str
    currentSkill: Optional[str] = None
    estimatedRemaining: int = Field(ge=0)
    timestamp: datetime


class CompletionFeedback(BaseModel):
    taskId: str
    status: TaskStatus
    progress: float
    message: str
    result: Optional[Dict[str, Any]] = None
    actualDuration: int
    timestamp: datetime


class FailureFeedback(BaseModel):
    taskId: str
    status: TaskStatus
    progress: float
    message: str
    error: ErrorInfo
    retryable: bool
    actualDuration: int
    timestamp: datetime


class Alert(BaseModel):
    taskId: str
    level: AlertLevel
    message: str
    details: str
    actionRequired: bool
    timestamp: datetime


class LogEntry(BaseModel):
    timestamp: datetime
    level: LogLevel
    taskId: Optional[str] = None
    skillId: Optional[str] = None
    message: str
    details: Dict[str, Any] = Field(default_factory=dict)


class SkillDefinition(BaseModel):
    name: str
    description: str
    priority: Priority
    category: str
    enabled: bool = True
