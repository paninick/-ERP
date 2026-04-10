from datetime import datetime
from mcp.types import (
    MCPSkillRequest,
    SkillInvocation,
    TaskType,
    Priority,
    Task,
    TaskStatus,
    SkillResult,
    SkillStatus,
)


def test_mcp_skill_request_validation():
    request = MCPSkillRequest(
        taskId="test-001",
        taskType=TaskType.SEARCH,
        priority=Priority.P0,
        skills=[
            SkillInvocation(name="SearchCodebase", parameters={"information_request": "test"})
        ]
    )

    assert request.taskId == "test-001"
    assert request.taskType == TaskType.SEARCH
    assert request.priority == Priority.P0
    assert len(request.skills) == 1
    assert request.skills[0].name == "SearchCodebase"
    assert request.executionMode == "SYNC"


def test_task_creation():
    now = datetime.now()
    task = Task(
        taskId="task-001",
        taskName="Test Task",
        taskType=TaskType.SEARCH,
        priority=Priority.P0,
        status=TaskStatus.PENDING,
        progress=0.0,
        skills=[
            SkillResult(
                skillId="skill-001",
                skillName="test",
                parameters={},
                status=SkillStatus.PENDING
            )
        ],
        createdAt=now,
        createdBy="test"
    )

    assert task.taskId == "task-001"
    assert task.status == TaskStatus.PENDING
    assert task.progress == 0.0
