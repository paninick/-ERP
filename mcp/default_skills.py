import os
from typing import Any, List
from .core import MCP
from .types import Priority


def echo_handler(message: str = "Hello") -> dict:
    return {"message": f"Echo: {message}"}


def add_handler(a: float, b: float) -> dict:
    return {"result": a + b, "a": a, "b": b}


def list_directory_handler(path: str = ".") -> dict:
    if not os.path.exists(path):
        return {"error": f"Path does not exist: {path}", "items": []}
    items = os.listdir(path)
    return {
        "path": os.path.abspath(path),
        "items": items,
        "count": len(items)
    }


def get_env_handler(name: str | None = None) -> dict:
    if name:
        return {"name": name, "value": os.environ.get(name)}
    return {"env": dict(os.environ)}


def register_all_default_skills(mcp: MCP) -> None:
    mcp.register_skill(
        name="echo",
        description="Echo back the input message",
        priority=Priority.P2,
        category="utility",
        handler=echo_handler
    )

    mcp.register_skill(
        name="add",
        description="Add two numbers",
        priority=Priority.P2,
        category="utility",
        handler=add_handler
    )

    mcp.register_skill(
        name="list_directory",
        description="List contents of a directory",
        priority=Priority.P1,
        category="system",
        handler=list_directory_handler
    )

    mcp.register_skill(
        name="get_env",
        description="Get environment variable(s)",
        priority=Priority.P1,
        category="system",
        handler=get_env_handler
    )
