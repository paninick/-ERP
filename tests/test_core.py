from mcp.types import MCPSkillRequest, SkillInvocation, TaskType, Priority
from mcp.exceptions import SkillNotFoundException


def test_mcp_initialization(mcp):
    assert mcp is not None
    assert mcp.skill_registry is not None
    assert mcp.task_manager is not None


def test_register_skill(mcp):
    def test_handler(x: int, y: int) -> int:
        return x + y

    skill = mcp.register_skill(
        name="test_add",
        description="Test addition skill",
        priority=Priority.P2,
        category="test",
        handler=test_handler
    )

    assert skill.name == "test_add"
    assert skill.enabled is True
    assert mcp.skill_registry.is_enabled("test_add")


def test_execute_echo_skill(mcp):
    mcp.register_skill(
        name="echo",
        description="Echo",
        priority=Priority.P2,
        category="utility",
        handler=lambda message: {"message": message}
    )

    request = MCPSkillRequest(
        taskId="test-echo",
        taskType=TaskType.FILE_OPERATION,
        priority=Priority.P0,
        skills=[
            SkillInvocation(name="echo", parameters={"message": "Hello World"})
        ]
    )

    response = mcp.execute(request)

    assert response.code == 200
    assert response.status == "SUCCESS"
    assert response.data.skill == "echo"
    assert response.data.result == {"message": "Hello World"}


def test_execute_skill_not_found(mcp):
    request = MCPSkillRequest(
        taskId="test-not-found",
        taskType=TaskType.SEARCH,
        priority=Priority.P0,
        skills=[
            SkillInvocation(name="nonexistent", parameters={})
        ]
    )

    response = mcp.execute(request)

    assert response.code == 404
    assert response.status == "FAILED"
    assert response.error is not None
    assert "Skill not found" in response.error.message


def test_get_statistics(mcp):
    stats = mcp.get_statistics()

    assert "uptime_seconds" in stats
    assert "skills_total" in stats
    assert "skills_enabled" in stats
    assert "task_statistics" in stats
    assert "total_tasks" in stats["task_statistics"]
