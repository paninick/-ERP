
from pydantic import BaseModel, Field
from typing import Optional

class TryOnRequest(BaseModel):
    style: str = Field(default="original", description="试穿风格: original, daily, ecommerce, fashion")

class TryOnResponse(BaseModel):
    success: bool
    message: str
    image_url: Optional[str] = None

class HealthResponse(BaseModel):
    status: str
    version: str

