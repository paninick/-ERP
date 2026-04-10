
@echo off
echo ========================================
echo 🎨 AnyTryOn - AI 虚拟试穿系统
echo ========================================
echo.

echo [1/3] 🐍 创建 Python 虚拟环境...
cd backend
if not exist venv (
    python -m venv venv
)
echo.

echo [2/3] 📦 安装 Python 依赖...
call venv\Scripts\activate
pip install -r requirements.txt
echo.

echo [3/3] 🚀 启动后端 API 服务...
echo.
echo 后端将在 http://localhost:8000 运行
echo API 文档: http://localhost:8000/docs
echo.
python main.py

pause

