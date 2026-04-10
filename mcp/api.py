from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from fastapi.staticfiles import StaticFiles
from fastapi.responses import FileResponse
from typing import Dict, List, Any
from pathlib import Path
from .types import MCPSkillRequest, MCPSuccessResponse, MCPFailureResponse, Task, TaskStatus, SkillDefinition
from .core import MCP


BASE_DIR = Path(__file__).resolve().parent.parent
STATIC_DIR = BASE_DIR / "static"

app = FastAPI(
    title="MCP (Master Control Program) API",
    description="Expert Group Skill Invocation System - MCP Execution Hub API",
    version="1.0.0"
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

mcp_instance: MCP | None = None


def get_mcp() -> MCP:
    global mcp_instance
    if mcp_instance is None:
        mcp_instance = MCP()
        register_default_skills(mcp_instance)
    return mcp_instance


def register_default_skills(mcp: MCP) -> None:
    from .default_skills import register_all_default_skills
    register_all_default_skills(mcp)


@app.get("/")
async def root() -> Dict[str, Any]:
    mcp = get_mcp()
    return {
        "name": "MCP (Master Control Program)",
        "version": "1.0.0",
        "description": "Expert Group Skill Invocation System",
        "statistics": mcp.get_statistics()
    }


@app.post("/execute", response_model=MCPSuccessResponse | MCPFailureResponse)
async def execute(request: MCPSkillRequest) -> MCPSuccessResponse | MCPFailureResponse:
    mcp = get_mcp()
    return mcp.execute(request)


@app.post("/execute_with_retry", response_model=MCPSuccessResponse | MCPFailureResponse)
async def execute_with_retry(
    request: MCPSkillRequest,
    max_retries: int = 3
) -> MCPSuccessResponse | MCPFailureResponse:
    from .retry import RetryStrategy
    mcp = get_mcp()
    retry_strategy = RetryStrategy(max_retries=max_retries)
    return mcp.execute_with_retry(request, retry_strategy)


@app.get("/tasks", response_model=List[Task])
async def list_tasks(status: str | None = None, limit: int = 100) -> List[Task]:
    mcp = get_mcp()
    task_status = None
    if status:
        try:
            task_status = TaskStatus(status)
        except ValueError:
            raise HTTPException(status_code=400, detail=f"Invalid status: {status}")
    return mcp.list_tasks(task_status, limit)


@app.get("/tasks/{task_id}", response_model=Task)
async def get_task(task_id: str) -> Task:
    mcp = get_mcp()
    task = mcp.get_task(task_id)
    if task is None:
        raise HTTPException(status_code=404, detail=f"Task not found: {task_id}")
    return task


@app.delete("/tasks/{task_id}")
async def cancel_task(task_id: str, reason: str = "Task cancelled by API") -> Dict[str, bool]:
    mcp = get_mcp()
    task = mcp.get_task(task_id)
    if task is None:
        raise HTTPException(status_code=404, detail=f"Task not found: {task_id}")
    mcp.cancel_task(task_id, reason)
    return {"success": True}


@app.get("/skills", response_model=List[SkillDefinition])
async def list_skills() -> List[SkillDefinition]:
    mcp = get_mcp()
    return mcp.list_available_skills()


@app.get("/statistics")
async def get_statistics() -> Dict[str, Any]:
    mcp = get_mcp()
    return mcp.get_statistics()


@app.post("/reset")
async def reset_statistics() -> Dict[str, bool]:
    mcp = get_mcp()
    mcp.skill_registry.clear()
    register_default_skills(mcp)
    return {"success": True}


if STATIC_DIR.exists():
    app.mount("/static", StaticFiles(directory=str(STATIC_DIR)), name="static")


@app.get("/ui")
async def get_ui():
    index_file = STATIC_DIR / "index.html"
    if index_file.exists():
        return FileResponse(str(index_file))
    raise HTTPException(status_code=404, detail="UI file not found")
