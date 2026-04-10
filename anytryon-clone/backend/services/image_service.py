
from PIL import Image
import io
import uuid
from pathlib import Path
from core.config import get_settings

settings = get_settings()

class ImageService:
    def __init__(self):
        self.upload_dir = Path(settings.UPLOAD_DIR)
        self.output_dir = Path(settings.OUTPUT_DIR)
        self.upload_dir.mkdir(exist_ok=True)
        self.output_dir.mkdir(exist_ok=True)
    
    async def save_uploaded_image(self, file, prefix: str = "image") -&gt; Path:
        file_extension = file.filename.split(".")[-1] if "." in file.filename else "png"
        filename = f"{prefix}_{uuid.uuid4()}.{file_extension}"
        filepath = self.upload_dir / filename
        
        contents = await file.read()
        with open(filepath, "wb") as f:
            f.write(contents)
        
        return filepath
    
    def load_image(self, filepath: Path) -&gt; Image.Image:
        return Image.open(filepath)
    
    def save_result_image(self, image: Image.Image) -&gt; Path:
        filename = f"tryon_{uuid.uuid4()}.png"
        filepath = self.output_dir / filename
        image.save(filepath)
        return filepath

