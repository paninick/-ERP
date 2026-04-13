@echo off
chcp 65001 >nul
echo ========================================
echo   ERP 服务状态检查
echo ========================================
echo.

echo [后端服务 - 端口 8080]
netstat -ano | findstr ":8080 " | findstr "LISTENING" >nul
if %errorlevel% equ 0 (
    for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":8080" ^| findstr "LISTENING"') do echo   状态: 运行中 (PID: %%a)
) else (
    echo   状态: 未运行
)

echo.
echo [前端服务 - 端口 80]
netstat -ano | findstr ":80 " | findstr "LISTENING" >nul
if %errorlevel% equ 0 (
    for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":80" ^| findstr "LISTENING"') do echo   状态: 运行中 (PID: %%a)
) else (
    echo   状态: 未运行
)

echo.
echo [数据库服务 - 端口 3306]
netstat -ano | findstr ":3306 " | findstr "LISTENING" >nul
if %errorlevel% equ 0 (
    for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":3306" ^| findstr "LISTENING"') do echo   状态: 运行中 (PID: %%a)
) else (
    echo   状态: 未运行
)

echo.
echo ========================================
echo   访问地址:
echo   后端 API: http://localhost:8080
echo   前端页面: http://localhost:80
echo ========================================
pause