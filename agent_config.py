#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
AI Agent 配置系统
基于 learn-coding-agent 项目的最佳实践
"""

import json
from pathlib import Path


class ToolConfig:
    """工具配置"""
    
    def __init__(self, name, enabled=True, concurrency_safe=False, 
                 read_only=False, destructive=False, always_allow=False,
                 always_deny=False, always_ask=True, priority=0):
        self.name = name
        self.enabled = enabled
        self.concurrency_safe = concurrency_safe
        self.read_only = read_only
        self.destructive = destructive
        self.always_allow = always_allow
        self.always_deny = always_deny
        self.always_ask = always_ask
        self.priority = priority
    
    def to_dict(self):
        return {
            "enabled": self.enabled,
            "concurrency_safe": self.concurrency_safe,
            "read_only": self.read_only,
            "destructive": self.destructive,
            "always_allow": self.always_allow,
            "always_deny": self.always_deny,
            "always_ask": self.always_ask,
            "priority": self.priority,
        }


class FeatureConfig:
    """功能特性配置"""
    
    def __init__(self):
        # s01 - s12 层配置
        self.s01_the_loop = True
        self.s02_tool_dispatch = True
        self.s03_planning = True
        self.s04_sub_agents = False
        self.s05_knowledge_on_demand = False
        self.s06_context_compression = True
        self.s07_persistent_tasks = True
        self.s08_background_tasks = False
        self.s09_agent_teams = False
        self.s10_team_protocols = False
        self.s11_autonomous_agents = False
        self.s12_worktree_isolation = False
        
        # 其他功能
        self.auto_compact = True
        self.snip_compact = False
        self.context_collapse = False
        self.telemetry = False
        self.undercover_mode = False
    
    def to_dict(self):
        return {
            "s01_the_loop": self.s01_the_loop,
            "s02_tool_dispatch": self.s02_tool_dispatch,
            "s03_planning": self.s03_planning,
            "s04_sub_agents": self.s04_sub_agents,
            "s05_knowledge_on_demand": self.s05_knowledge_on_demand,
            "s06_context_compression": self.s06_context_compression,
            "s07_persistent_tasks": self.s07_persistent_tasks,
            "s08_background_tasks": self.s08_background_tasks,
            "s09_agent_teams": self.s09_agent_teams,
            "s10_team_protocols": self.s10_team_protocols,
            "s11_autonomous_agents": self.s11_autonomous_agents,
            "s12_worktree_isolation": self.s12_worktree_isolation,
            "auto_compact": self.auto_compact,
            "snip_compact": self.snip_compact,
            "context_collapse": self.context_collapse,
            "telemetry": self.telemetry,
            "undercover_mode": self.undercover_mode,
        }


class PersistenceConfig:
    """持久化配置"""
    
    def __init__(self):
        self.enabled = True
        self.storage_dir = "./agent_sessions"
        self.jsonl_messages = True
        self.json_metadata = True
        self.fsync_user_messages = True
        self.async_other_messages = True
        self.auto_save_interval = 10
    
    def to_dict(self):
        return {
            "enabled": self.enabled,
            "storage_dir": self.storage_dir,
            "jsonl_messages": self.jsonl_messages,
            "json_metadata": self.json_metadata,
            "fsync_user_messages": self.fsync_user_messages,
            "async_other_messages": self.async_other_messages,
            "auto_save_interval": self.auto_save_interval,
        }


class CompressionConfig:
    """上下文压缩配置"""
    
    def __init__(self):
        self.enabled = True
        self.token_threshold = 8000
        self.max_messages = 50
        self.keep_recent = 30
        self.keep_oldest = 10
        self.compact_boundary_marker = "[COMPACT_BOUNDARY]"
        self.use_llm_for_summary = True
        self.summary_model = "gpt-3.5-turbo"
    
    def to_dict(self):
        return {
            "enabled": self.enabled,
            "token_threshold": self.token_threshold,
            "max_messages": self.max_messages,
            "keep_recent": self.keep_recent,
            "keep_oldest": self.keep_oldest,
            "compact_boundary_marker": self.compact_boundary_marker,
            "use_llm_for_summary": self.use_llm_for_summary,
            "summary_model": self.summary_model,
        }


class PermissionConfig:
    """权限配置"""
    
    def __init__(self):
        self.enabled = True
        self.default_decision = "ask"
        self.auto_approve_read_only = True
        self.always_ask_destructive = True
        self.interactive_prompt = True
        self.remember_always_allow = True
        self.path_sandbox_enabled = True
        self.allowed_paths = []
    
    def to_dict(self):
        return {
            "enabled": self.enabled,
            "default_decision": self.default_decision,
            "auto_approve_read_only": self.auto_approve_read_only,
            "always_ask_destructive": self.always_ask_destructive,
            "interactive_prompt": self.interactive_prompt,
            "remember_always_allow": self.remember_always_allow,
            "path_sandbox_enabled": self.path_sandbox_enabled,
            "allowed_paths": self.allowed_paths,
        }


class SystemPromptConfig:
    """系统提示配置"""
    
    def __init__(self):
        self.include_tools = True
        self.include_permissions = True
        self.include_project_context = True
        self.include_claude_md = True
        self.claude_md_filename = "CLAUDE.md"
        self.language = "zh-CN"
        self.tone = "helpful"
    
    def to_dict(self):
        return {
            "include_tools": self.include_tools,
            "include_permissions": self.include_permissions,
            "include_project_context": self.include_project_context,
            "include_claude_md": self.include_claude_md,
            "claude_md_filename": self.claude_md_filename,
            "language": self.language,
            "tone": self.tone,
        }


class AgentConfig:
    """主 Agent 配置"""
    
    def __init__(self):
        # 基本信息
        self.name = "Coding Assistant"
        self.version = "1.0.0"
        self.description = "基于 learn-coding-agent 的智能编程助手"
        
        # 各模块配置
        self.features = FeatureConfig()
        self.persistence = PersistenceConfig()
        self.compression = CompressionConfig()
        self.permissions = PermissionConfig()
        self.system_prompt = SystemPromptConfig()
        
        # 工具配置
        self.tools = {}
        
        # 上下文配置
        self.max_context_tokens = 128000
        self.max_response_tokens = 4096
        self.temperature = 0.7
        
        # 模型配置
        self.primary_model = "gpt-4"
        self.fallback_model = "gpt-3.5-turbo"
        
        self._init_default_tools()
    
    def _init_default_tools(self):
        """初始化默认工具"""
        default_tools = [
            # 文件操作
            ToolConfig(name="file_read", enabled=True, read_only=True, always_allow=True),
            ToolConfig(name="file_write", enabled=True, destructive=True, always_ask=True),
            ToolConfig(name="file_edit", enabled=True, destructive=True, always_ask=True),
            
            # 搜索工具
            ToolConfig(name="glob", enabled=True, read_only=True, always_allow=True),
            ToolConfig(name="grep", enabled=True, read_only=True, always_allow=True),
            
            # 执行工具
            ToolConfig(name="bash", enabled=True, destructive=False, always_ask=True, priority=10),
            ToolConfig(name="powerShell", enabled=True, destructive=False, always_ask=True, priority=10),
            
            # 交互工具
            ToolConfig(name="ask_user", enabled=True, read_only=True, always_allow=True),
            
            # 网络工具
            ToolConfig(name="web_fetch", enabled=True, read_only=True, always_ask=True),
            ToolConfig(name="web_search", enabled=True, read_only=True, always_ask=True),
            
            # 规划工具
            ToolConfig(name="todo_write", enabled=True, read_only=False, always_allow=True),
            
            # MCP 工具
            ToolConfig(name="mcp", enabled=True, read_only=False, always_ask=True),
        ]
        
        for tool in default_tools:
            self.tools[tool.name] = tool
    
    def to_dict(self):
        """转换为字典"""
        return {
            "name": self.name,
            "version": self.version,
            "description": self.description,
            "features": self.features.to_dict(),
            "persistence": self.persistence.to_dict(),
            "compression": self.compression.to_dict(),
            "permissions": self.permissions.to_dict(),
            "system_prompt": self.system_prompt.to_dict(),
            "tools": {name: tool.to_dict() for name, tool in self.tools.items()},
            "max_context_tokens": self.max_context_tokens,
            "max_response_tokens": self.max_response_tokens,
            "temperature": self.temperature,
            "primary_model": self.primary_model,
            "fallback_model": self.fallback_model,
        }
    
    @classmethod
    def from_dict(cls, data):
        """从字典创建配置"""
        config = cls()
        
        # 基本信息
        config.name = data.get("name", "Coding Assistant")
        config.version = data.get("version", "1.0.0")
        config.description = data.get("description", "基于 learn-coding-agent 的智能编程助手")
        
        # 功能配置
        features_data = data.get("features", {})
        config.features.s01_the_loop = features_data.get("s01_the_loop", True)
        config.features.s02_tool_dispatch = features_data.get("s02_tool_dispatch", True)
        config.features.s03_planning = features_data.get("s03_planning", True)
        config.features.s04_sub_agents = features_data.get("s04_sub_agents", False)
        config.features.s05_knowledge_on_demand = features_data.get("s05_knowledge_on_demand", False)
        config.features.s06_context_compression = features_data.get("s06_context_compression", True)
        config.features.s07_persistent_tasks = features_data.get("s07_persistent_tasks", True)
        config.features.s08_background_tasks = features_data.get("s08_background_tasks", False)
        config.features.s09_agent_teams = features_data.get("s09_agent_teams", False)
        config.features.s10_team_protocols = features_data.get("s10_team_protocols", False)
        config.features.s11_autonomous_agents = features_data.get("s11_autonomous_agents", False)
        config.features.s12_worktree_isolation = features_data.get("s12_worktree_isolation", False)
        config.features.auto_compact = features_data.get("auto_compact", True)
        config.features.snip_compact = features_data.get("snip_compact", False)
        config.features.context_collapse = features_data.get("context_collapse", False)
        config.features.telemetry = features_data.get("telemetry", False)
        config.features.undercover_mode = features_data.get("undercover_mode", False)
        
        # 持久化配置
        pers_data = data.get("persistence", {})
        config.persistence.enabled = pers_data.get("enabled", True)
        config.persistence.storage_dir = pers_data.get("storage_dir", "./agent_sessions")
        config.persistence.jsonl_messages = pers_data.get("jsonl_messages", True)
        config.persistence.json_metadata = pers_data.get("json_metadata", True)
        config.persistence.fsync_user_messages = pers_data.get("fsync_user_messages", True)
        config.persistence.async_other_messages = pers_data.get("async_other_messages", True)
        config.persistence.auto_save_interval = pers_data.get("auto_save_interval", 10)
        
        # 压缩配置
        comp_data = data.get("compression", {})
        config.compression.enabled = comp_data.get("enabled", True)
        config.compression.token_threshold = comp_data.get("token_threshold", 8000)
        config.compression.max_messages = comp_data.get("max_messages", 50)
        config.compression.keep_recent = comp_data.get("keep_recent", 30)
        config.compression.keep_oldest = comp_data.get("keep_oldest", 10)
        config.compression.compact_boundary_marker = comp_data.get("compact_boundary_marker", "[COMPACT_BOUNDARY]")
        config.compression.use_llm_for_summary = comp_data.get("use_llm_for_summary", True)
        config.compression.summary_model = comp_data.get("summary_model", "gpt-3.5-turbo")
        
        # 权限配置
        perm_data = data.get("permissions", {})
        config.permissions.enabled = perm_data.get("enabled", True)
        config.permissions.default_decision = perm_data.get("default_decision", "ask")
        config.permissions.auto_approve_read_only = perm_data.get("auto_approve_read_only", True)
        config.permissions.always_ask_destructive = perm_data.get("always_ask_destructive", True)
        config.permissions.interactive_prompt = perm_data.get("interactive_prompt", True)
        config.permissions.remember_always_allow = perm_data.get("remember_always_allow", True)
        config.permissions.path_sandbox_enabled = perm_data.get("path_sandbox_enabled", True)
        config.permissions.allowed_paths = perm_data.get("allowed_paths", [])
        
        # 系统提示配置
        sp_data = data.get("system_prompt", {})
        config.system_prompt.include_tools = sp_data.get("include_tools", True)
        config.system_prompt.include_permissions = sp_data.get("include_permissions", True)
        config.system_prompt.include_project_context = sp_data.get("include_project_context", True)
        config.system_prompt.include_claude_md = sp_data.get("include_claude_md", True)
        config.system_prompt.claude_md_filename = sp_data.get("claude_md_filename", "CLAUDE.md")
        config.system_prompt.language = sp_data.get("language", "zh-CN")
        config.system_prompt.tone = sp_data.get("tone", "helpful")
        
        # 工具配置
        tools_data = data.get("tools", {})
        config.tools = {}
        for name, tool_data in tools_data.items():
            config.tools[name] = ToolConfig(
                name=name,
                enabled=tool_data.get("enabled", True),
                concurrency_safe=tool_data.get("concurrency_safe", False),
                read_only=tool_data.get("read_only", False),
                destructive=tool_data.get("destructive", False),
                always_allow=tool_data.get("always_allow", False),
                always_deny=tool_data.get("always_deny", False),
                always_ask=tool_data.get("always_ask", True),
                priority=tool_data.get("priority", 0),
            )
        
        # 其他配置
        config.max_context_tokens = data.get("max_context_tokens", 128000)
        config.max_response_tokens = data.get("max_response_tokens", 4096)
        config.temperature = data.get("temperature", 0.7)
        config.primary_model = data.get("primary_model", "gpt-4")
        config.fallback_model = data.get("fallback_model", "gpt-3.5-turbo")
        
        return config
    
    def save(self, filepath):
        """保存配置到文件"""
        Path(filepath).parent.mkdir(parents=True, exist_ok=True)
        with open(filepath, "w", encoding="utf-8") as f:
            json.dump(self.to_dict(), f, ensure_ascii=False, indent=2)
    
    @classmethod
    def load(cls, filepath):
        """从文件加载配置"""
        with open(filepath, "r", encoding="utf-8") as f:
            data = json.load(f)
        return cls.from_dict(data)


# 预设配置
def get_mvp_config():
    """获取 MVP 配置（最小可行产品）"""
    config = AgentConfig()
    config.name = "Coding Assistant (MVP)"
    config.description = "最小可行产品配置"
    
    # 只启用核心功能
    config.features.s01_the_loop = True
    config.features.s02_tool_dispatch = True
    config.features.s06_context_compression = True
    config.features.s07_persistent_tasks = True
    
    # 禁用高级功能
    config.features.s04_sub_agents = False
    config.features.s05_knowledge_on_demand = False
    config.features.s08_background_tasks = False
    config.features.s09_agent_teams = False
    config.features.s10_team_protocols = False
    config.features.s11_autonomous_agents = False
    config.features.s12_worktree_isolation = False
    
    return config


def get_production_config():
    """获取生产环境配置"""
    config = AgentConfig()
    config.name = "Coding Assistant (Production)"
    config.description = "生产环境配置"
    
    # 启用所有核心功能
    config.features.s01_the_loop = True
    config.features.s02_tool_dispatch = True
    config.features.s03_planning = True
    config.features.s06_context_compression = True
    config.features.s07_persistent_tasks = True
    
    # 启用压缩策略
    config.features.auto_compact = True
    config.compression.token_threshold = 8000
    
    # 生产环境权限严格
    config.permissions.enabled = True
    config.permissions.always_ask_destructive = True
    config.permissions.path_sandbox_enabled = True
    
    # 启用持久化
    config.persistence.enabled = True
    config.persistence.fsync_user_messages = True
    
    return config


def get_advanced_config():
    """获取高级功能配置"""
    config = AgentConfig()
    config.name = "Coding Assistant (Advanced)"
    config.description = "高级功能配置"
    
    # 启用所有功能
    config.features.s01_the_loop = True
    config.features.s02_tool_dispatch = True
    config.features.s03_planning = True
    config.features.s04_sub_agents = True
    config.features.s05_knowledge_on_demand = True
    config.features.s06_context_compression = True
    config.features.s07_persistent_tasks = True
    config.features.s08_background_tasks = True
    config.features.s09_agent_teams = True
    config.features.s10_team_protocols = True
    config.features.s11_autonomous_agents = True
    config.features.s12_worktree_isolation = True
    
    # 启用所有压缩策略
    config.features.auto_compact = True
    config.features.snip_compact = True
    config.features.context_collapse = True
    
    return config


def main():
    """示例：使用配置系统"""
    print("=" * 60)
    print("AI Agent 配置系统 - 示例")
    print("=" * 60)
    
    # 1. 创建默认配置
    print("\n1. 创建默认配置...")
    default_config = AgentConfig()
    print(f"   名称: {default_config.name}")
    print(f"   版本: {default_config.version}")
    
    # 2. 创建 MVP 配置
    print("\n2. 创建 MVP 配置...")
    mvp_config = get_mvp_config()
    print(f"   名称: {mvp_config.name}")
    print(f"   启用功能: s01, s02, s06, s07")
    
    # 3. 保存配置
    print("\n3. 保存配置到文件...")
    default_config.save("./config/default_config.json")
    mvp_config.save("./config/mvp_config.json")
    print("   配置已保存到 ./config/")
    
    # 4. 加载配置
    print("\n4. 从文件加载配置...")
    loaded_config = AgentConfig.load("./config/default_config.json")
    print(f"   已加载: {loaded_config.name}")
    
    # 5. 创建并保存生产配置
    print("\n5. 创建生产配置...")
    prod_config = get_production_config()
    prod_config.save("./config/production_config.json")
    print("   生产配置已保存")
    
    print("\n" + "=" * 60)
    print("配置系统示例完成！")
    print("=" * 60)


if __name__ == "__main__":
    main()
