@echo off
chcp 65001 >nul
:menu
cls
echo ========================================
echo     ERP 服务管理工具
echo ========================================
echo.
echo   1. 启动 ERP 服务
echo   2. 停止 ERP 服务
echo   3. 查看服务状态
echo   4. 设置开机自启
echo   5. 取消开机自启
echo   6. 访问 ERP (打开浏览器)
echo   0. 退出
echo.
echo ========================================
set /p choice=请选择操作 (0-6):

if "%choice%"=="1" goto start
if "%choice%"=="2" goto stop
if "%choice%"=="3" goto status
if "%choice%"=="4" goto enable
if "%choice%"=="5" goto disable
if "%choice%"=="6" goto open
if "%choice%"=="0" exit

echo 无效选择，请重新选择
timeout /t 2 >nul
goto menu

:start
cls
echo 正在启动 ERP 服务...
call "%~dp0start-erp.bat"
goto menu

:stop
cls
call "%~dp0stop-erp.bat"
goto menu

:status
cls
call "%~dp0check-erp-status.bat"
goto menu

:enable
cls
call "%~dp0setup-auto-startup.bat"
goto menu

:disable
cls
schtasks /delete /tn "ERP自动启动" /f >nul 2>&1
echo 已取消开机自动启动
timeout /t 2 >nul
goto menu

:open
start http://localhost:80
goto menu