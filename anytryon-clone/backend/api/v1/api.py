
from fastapi import APIRouter
from api.v1.endpoints import tryon

api_router = APIRouter()

api_router.include_router(tryon.router, prefix="/tryon", tags=["虚拟试穿"])

