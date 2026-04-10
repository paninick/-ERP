
@echo off
echo ========================================
echo 🎨 AnyTryOn - AI 虚拟试穿系统
echo ========================================
echo.

echo [1/2] 📦 安装前端依赖...
cd frontend
if not exist node_modules (
    call npm install
)
echo.

echo [2/2] 🚀 启动前端开发服务器...
echo.
echo 前端将在 http://localhost:3000 运行
echo.
call npm run dev

pause

