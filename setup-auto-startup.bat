@echo off
chcp 65001 >nul
echo ========================================
echo   ERP 开机自动启动设置
echo ========================================
echo.

set SCRIPT_PATH=%~dp0start-erp.bat

schtasks /create /tn "ERP自动启动" /tr "\"%SCRIPT_PATH%\"" /sc onlogon /rl limited /f >nul 2>&1

if %errorlevel% equ 0 (
    echo [成功] 已设置开机自动启动 ERP
    echo   任务名称: ERP自动启动
    echo   启动方式: 用户登录后自动运行
) else (
    echo [失败] 设置失败，请以管理员身份运行
)

echo.
pause