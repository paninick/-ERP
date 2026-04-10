from mcp.types import TaskType, Priority, TaskStatus, SkillStatus
from mcp.exceptions import ConcurrentExecutionException


def test_create_task(task_manager):
    task = task_manager.create_task(
        task_name="Test Task",
        task_type=TaskType.SEARCH,
        priority=Priority.P0,
        skill_names=["SearchCodebase", "Grep"]
    )

    assert task.taskId is not None
    assert task.taskName == "Test Task"
    assert task.taskType == TaskType.SEARCH
    assert task.priority == Priority.P0
    assert task.status == TaskStatus.PENDING
    assert task.progress == 0.0
    assert len(task.skills) == 2
    assert task.skills[0].status == SkillStatus.PENDING
    assert task.createdAt is not None


def test_start_task(task_manager):
    task = task_manager.create_task(
        task_name="Test Task",
        task_type=TaskType.SEARCH,
        priority=Priority.P0,
        skill_names=["SearchCodebase"]
    )

    started = task_manager.start_task(task.taskId)

    assert started.status == TaskStatus.RUNNING
    assert started.startedAt is not None


def test_start_already_running_task(task_manager):
    task = task_manager.create_task(
        task_name="Test Task",
        task_type=TaskType.SEARCH,
        priority=Priority.P0,
        skill_names=["SearchCodebase"]
    )

    task_manager.start_task(task.taskId)

    try:
        task_manager.start_task(task.taskId)
        assert False, "Should have raised ConcurrentExecutionException"
    except ConcurrentExecutionException:
        assert True


def test_complete_task(task_manager):
    task = task_manager.create_task(
        task_name="Test Task",
        task_type=TaskType.SEARCH,
        priority=Priority.P0,
        skill_names=["SearchCodebase"]
    )

    task_manager.start_task(task.taskId)
    feedback = task_manager.complete_task(
        task.taskId,
        result={"found": True}
    )

    updated_task = task_manager.get_task(task.taskId)

    assert updated_task.status == TaskStatus.COMPLETED
    assert updated_task.progress == 100.0
    assert updated_task.completedAt is not None
    assert feedback.status == TaskStatus.COMPLETED
    assert feedback.progress == 100.0


def test_get_statistics(task_manager):
    task_manager.create_task(
        task_name="Task 1",
        task_type=TaskType.SEARCH,
        priority=Priority.P0,
        skill_names=["SearchCodebase"]
    )

    stats = task_manager.get_statistics()

    assert stats["total_tasks"] == 1
    assert stats["pending"] == 1
    assert stats["completed"] == 0
    assert stats["failed"] == 0
