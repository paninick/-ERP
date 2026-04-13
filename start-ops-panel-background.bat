@echo off
chcp 65001 > nul
title ERP运维管理面板

REM 设置工作目录
cd /d "%~dp0ops-panel"

echo 正在启动ERP运维管理面板...
echo.

REM 检查端口是否被占用
netstat -ano | findstr ":9999" >nul 2>&1
if not errorlevel 1 (
    echo 端口9999已被占用，正在结束旧进程...
    for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":9999"') do (
        taskkill /F /PID %%a >nul 2>&1
    )
    timeout /t 2 /nobreak >nul
)

REM 使用PowerShell在后台启动
powershell -WindowStyle Hidden -Command "cd '%~dp0ops-panel'; python server.py"

timeout /t 3 /nobreak >nul

REM 验证是否启动成功
netstat -ano | findstr ":9999" >nul 2>&1
if errorlevel 1 (
    echo ❌ 启动失败，请检查日志
    pause
    exit /b 1
)

echo ========================================
echo ✅ ERP运维管理面板已启动！
echo.
echo 访问地址：http://localhost:9999
echo.
echo 提示：
echo - 此工具在后台独立运行
echo - 不依赖其他服务进程
echo - 可随时通过 start-ops-panel.bat 重新启动
echo ========================================
echo.
timeout /t 5 /nobreak >nul
