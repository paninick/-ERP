
from fastapi import APIRouter, UploadFile, File, Form
from fastapi.responses import FileResponse
from services.tryon_service import TryOnService
from schemas.tryon import TryOnResponse

router = APIRouter(prefix="/tryon", tags=["虚拟试穿"])

tryon_service = TryOnService()

@router.post("/generate", response_model=TryOnResponse)
async def generate_tryon(
    model_image: UploadFile = File(..., description="人物照片"),
    top_image: UploadFile = File(..., description="上衣图片"),
    bottom_image: UploadFile = File(None, description="下装图片（可选）"),
    style: str = Form("original", description="试穿风格")
):
    print(f"收到生成请求，风格: {style}")
    
    try:
        model_img_data = await model_image.read()
        top_img_data = await top_image.read()
        
        bottom_img_data = None
        if bottom_image:
            bottom_img_data = await bottom_image.read()
        
        print(f"图片加载成功！")
        
        output_path = await tryon_service.generate_tryon(
            model_img_data, 
            top_img_data, 
            bottom_img_data, 
            style
        )
        
        print(f"结果已保存到: {output_path}")
        
        return FileResponse(
            path=output_path,
            media_type="image/png",
            filename=output_path.name
        )
        
    except Exception as e:
        print(f"错误: {e}")
        return TryOnResponse(
            success=False,
            message=f"生成失败: {str(e)}"
        )

