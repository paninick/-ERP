
from PIL import Image
import io
import uuid
from pathlib import Path
from core.config import get_settings

settings = get_settings()

class TryOnService:
    def __init__(self):
        self.upload_dir = Path(settings.UPLOAD_DIR)
        self.output_dir = Path(settings.OUTPUT_DIR)
        self.upload_dir.mkdir(exist_ok=True)
        self.output_dir.mkdir(exist_ok=True)
    
    async def generate_tryon(self, model_img_bytes, top_img_bytes, bottom_img_bytes=None, style="original"):
        try:
            print("使用本地模拟模式")
            model_img = Image.open(io.BytesIO(model_img_bytes))
            output_filename = f"tryon_{uuid.uuid4()}.png"
            output_path = self.output_dir / output_filename
            model_img.save(output_path)
            return output_path
            
        except Exception as e:
            print(f"生成试穿效果失败: {e}")
            raise

