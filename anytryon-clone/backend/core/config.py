
from pydantic_settings import BaseSettings
from functools import lru_cache

class Settings(BaseSettings):
    PROJECT_NAME: str = "AnyTryOn API"
    VERSION: str = "1.0.0"
    API_V1_PREFIX: str = "/api/v1"
    
    FRONTEND_URL: str = "http://localhost:3000"
    
    UPLOAD_DIR: str = "uploads"
    OUTPUT_DIR: str = "outputs"
    
    MAX_UPLOAD_SIZE: int = 10 * 1024 * 1024
    
    class Config:
        env_file = ".env"

@lru_cache()
def get_settings():
    return Settings()

