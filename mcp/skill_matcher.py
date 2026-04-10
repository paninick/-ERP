from typing import List, Optional, Dict
from .types import SkillDefinition, Priority, TaskType
from .skill_registry import SkillRegistry


class SkillMatcher:
    def __init__(self, registry: SkillRegistry):
        self.registry = registry

    def match_skills(
        self,
        task_type: TaskType,
        required_skills: Optional[List[str]] = None
    ) -> List[SkillDefinition]:
        if required_skills:
            matched = []
            for skill_name in required_skills:
                skill = self.registry.get_skill(skill_name)
                if skill and skill.enabled:
                    matched.append(skill)
            matched.sort(key=lambda x: x.priority.value)
            return matched

        return self.registry.find_matching_skills(task_type.value)

    def get_best_match(
        self,
        task_type: TaskType,
        required_skills: Optional[List[str]] = None
    ) -> Optional[SkillDefinition]:
        matched = self.match_skills(task_type, required_skills)
        return matched[0] if matched else None

    @staticmethod
    def get_matching_priority() -> Dict[str, int]:
        return {
            "search": {
                "search": 0,
                "SearchCodebase": 1,
                "Grep": 2,
                "Glob": 3
            }
        }

    @staticmethod
    def select_search_skill(
        is_complex: bool = False,
        is_semantic: bool = False,
        is_exact: bool = False,
        is_file_find: bool = False
    ) -> str:
        if is_complex:
            return "search"
        elif is_semantic:
            return "SearchCodebase"
        elif is_exact:
            return "Grep"
        elif is_file_find:
            return "Glob"
        return "SearchCodebase"

    @staticmethod
    def select_file_operation_skill(is_create: bool = False) -> str:
        if is_create:
            return "Write"
        return "Edit"

    @staticmethod
    def should_use_todo_write(task_count: int) -> bool:
        return task_count >= 3

    def sort_by_priority(self, skills: List[SkillDefinition]) -> List[SkillDefinition]:
        priority_order = {"P0": 0, "P1": 1, "P2": 2}
        return sorted(skills, key=lambda s: priority_order[s.priority.value])
