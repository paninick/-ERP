import pytest
from mcp.core import MCP
from mcp.skill_registry import SkillRegistry
from mcp.task_manager import TaskManager


@pytest.fixture
def mcp():
    return MCP()


@pytest.fixture
def skill_registry():
    return SkillRegistry()


@pytest.fixture
def task_manager():
    return TaskManager()
