from mcp.types import Priority
from mcp.exceptions import SkillNotFoundException


def test_register_skill(skill_registry):
    def handler():
        return "test"

    skill = skill_registry.register(
        name="test_skill",
        description="Test skill",
        priority=Priority.P0,
        category="test",
        handler=handler
    )

    assert skill.name == "test_skill"
    assert skill.description == "Test skill"
    assert skill.priority == Priority.P0
    assert skill.category == "test"
    assert skill.enabled is True
    assert skill_registry.get_skill("test_skill") is not None
    assert skill_registry.get_handler("test_skill") is handler


def test_invoke_skill(skill_registry):
    def add_handler(a: int, b: int) -> int:
        return a + b

    skill_registry.register(
        name="add",
        description="Add two numbers",
        priority=Priority.P0,
        category="test",
        handler=add_handler
    )

    result = skill_registry.invoke_skill("add", {"a": 2, "b": 3})

    assert result == 5


def test_invoke_not_found(skill_registry):
    try:
        skill_registry.invoke_skill("nonexistent", {})
        assert False, "Should have raised SkillNotFoundException"
    except SkillNotFoundException:
        assert True


def test_disable_enable_skill(skill_registry):
    skill_registry.register(
        name="test",
        description="Test",
        priority=Priority.P0,
        category="test",
        handler=lambda: None
    )

    assert skill_registry.is_enabled("test") is True

    skill_registry.disable_skill("test")
    assert skill_registry.is_enabled("test") is False

    skill_registry.enable_skill("test")
    assert skill_registry.is_enabled("test") is True


def test_list_skills_by_category(skill_registry):
    skill_registry.register(
        name="skill1",
        description="Skill 1",
        priority=Priority.P0,
        category="search",
        handler=lambda: None
    )
    skill_registry.register(
        name="skill2",
        description="Skill 2",
        priority=Priority.P0,
        category="search",
        handler=lambda: None
    )
    skill_registry.register(
        name="skill3",
        description="Skill 3",
        priority=Priority.P0,
        category="file",
        handler=lambda: None
    )

    search_skills = skill_registry.list_skills_by_category("search")
    assert len(search_skills) == 2

    file_skills = skill_registry.list_skills_by_category("file")
    assert len(file_skills) == 1
