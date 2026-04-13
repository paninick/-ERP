@echo off
chcp 65001 > nul
echo ========================================
echo    ERP运维管理面板 - 独立启动器
echo ========================================
echo.

REM 设置工作目录
cd /d "%~dp0ops-panel"

echo [1/4] 检查Python环境...
python --version >nul 2>&1
if errorlevel 1 (
    echo ❌ 错误：未找到Python，请先安装Python 3.7+
    pause
    exit /b 1
)
echo ✅ Python环境正常
echo.

echo [2/4] 检查Flask依赖...
python -c "import flask" >nul 2>&1
if errorlevel 1 (
    echo ⚠️  警告：未安装Flask，正在安装...
    pip install flask
    if errorlevel 1 (
        echo ❌ 错误：Flask安装失败
        pause
        exit /b 1
    )
)
echo ✅ Flask依赖正常
echo.

echo [3/4] 检查端口9999...
netstat -ano | findstr ":9999" >nul 2>&1
if not errorlevel 1 (
    echo ⚠️  警告：端口9999已被占用，尝试结束占用进程...
    for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":9999"') do (
        taskkill /F /PID %%a >nul 2>&1
    )
    timeout /t 2 /nobreak >nul
)
echo ✅ 端口9999可用
echo.

echo [4/4] 启动运维管理面板...
echo.
echo ========================================
echo   运维面板启动成功！
echo   访问地址：http://localhost:9999
echo.
echo   提示：
echo   - 此工具完全独立运行，不依赖其他服务
echo   - 即使前后端服务关闭，此工具仍可正常使用
echo   - 按 Ctrl+C 可停止服务
echo ========================================
echo.

python server.py

pause
