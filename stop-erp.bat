@echo off
chcp 65001 >nul
echo ========================================
echo   ERP 系统停止脚本
echo ========================================
echo.

echo 正在停止 ERP 服务...

for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8080" ^| findstr "LISTENING"') do (
    echo 停止后端服务 (PID: %%a)...
    taskkill /f /pid %%a >nul 2>&1
)

for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":80" ^| findstr "LISTENING"') do (
    echo 停止前端服务 (PID: %%a)...
    taskkill /f /pid %%a >nul 2>&1
)

echo.
echo ========================================
echo   ERP 已停止
echo ========================================
pause