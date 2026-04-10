from typing import Dict, List, Optional, Any, Callable
from .types import SkillDefinition, Priority
from .exceptions import SkillNotFoundException


class SkillRegistry:
    def __init__(self):
        self._skills: Dict[str, SkillDefinition] = {}
        self._handlers: Dict[str, Callable] = {}

    def register(
        self,
        name: str,
        description: str,
        priority: Priority,
        category: str,
        handler: Callable,
        enabled: bool = True
    ) -> SkillDefinition:
        skill = SkillDefinition(
            name=name,
            description=description,
            priority=priority,
            category=category,
            enabled=enabled
        )
        self._skills[name] = skill
        self._handlers[name] = handler
        return skill

    def get_skill(self, name: str) -> Optional[SkillDefinition]:
        return self._skills.get(name)

    def get_handler(self, name: str) -> Optional[Callable]:
        return self._handlers.get(name)

    def list_skills(self) -> List[SkillDefinition]:
        return list(self._skills.values())

    def list_skills_by_category(self, category: str) -> List[SkillDefinition]:
        return [s for s in self._skills.values() if s.category == category and s.enabled]

    def list_skills_by_priority(self, priority: Priority) -> List[SkillDefinition]:
        return [s for s in self._skills.values() if s.priority == priority and s.enabled]

    def enable_skill(self, name: str) -> bool:
        if name in self._skills:
            self._skills[name].enabled = True
            return True
        return False

    def disable_skill(self, name: str) -> bool:
        if name in self._skills:
            self._skills[name].enabled = False
            return True
        return False

    def is_enabled(self, name: str) -> bool:
        skill = self._skills.get(name)
        return skill is not None and skill.enabled

    def find_matching_skills(
        self,
        task_type: str,
        required_skills: Optional[List[str]] = None
    ) -> List[SkillDefinition]:
        matching = []
        for skill in self._skills.values():
            if not skill.enabled:
                continue
            if required_skills and skill.name in required_skills:
                matching.append(skill)
            elif skill.category == task_type:
                matching.append(skill)

        matching.sort(key=lambda x: x.priority.value)
        return matching

    def invoke_skill(self, name: str, parameters: Dict[str, Any]) -> Any:
        if name not in self._skills or not self.is_enabled(name):
            raise SkillNotFoundException(name)

        handler = self._handlers.get(name)
        if handler is None:
            raise SkillNotFoundException(name, f"No handler registered for skill: {name}")

        return handler(**parameters)

    def unregister(self, name: str) -> bool:
        if name in self._skills:
            del self._skills[name]
            if name in self._handlers:
                del self._handlers[name]
            return True
        return False

    def clear(self) -> None:
        self._skills.clear()
        self._handlers.clear()
