@echo off
chcp 65001 > nul
echo 正在停止ERP运维管理面板...

REM 查找并停止占用9999端口的进程
netstat -ano | findstr ":9999" >nul 2>&1
if errorlevel 1 (
    echo 运维管理面板未在运行
    pause
    exit /b 0
)

for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":9999"') do (
    echo 正在停止进程 PID: %%a
    taskkill /F /PID %%a >nul 2>&1
)

timeout /t 2 /nobreak >nul

REM 验证是否停止成功
netstat -ano | findstr ":9999" >nul 2>&1
if errorlevel 1 (
    echo ✅ 运维管理面板已停止
) else (
    echo ⚠️  可能还有进程在运行，请手动检查
)

pause
