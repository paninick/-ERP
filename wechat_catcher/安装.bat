@echo off
chcp 65001 >nul
title 微信群消息抓取 - 安装向导

echo.
echo ========================================
echo     微信群消息抓取工具 - 安装向导
echo ========================================
echo.

set "SCRIPT_DIR=%~dp0"
set "INSTALL_DIR=%USERPROFILE%\Documents\WeChatCatcher"

echo 正在创建安装目录...
if not exist "%INSTALL_DIR%" (
    mkdir "%INSTALL_DIR%"
)

echo 正在复制文件...
xcopy /E /Y "%SCRIPT_DIR%main.py" "%INSTALL_DIR%\" >nul
xcopy /E /Y "%SCRIPT_DIR%requirements.txt" "%INSTALL_DIR%\" >nul
if exist "%SCRIPT_DIR%output" (
    xcopy /E /Y "%SCRIPT_DIR%output" "%INSTALL_DIR%\" >nul
)

echo.
echo 正在检查Python环境...
python --version >nul 2>&1
if errorlevel 1 (
    echo [错误] 未检测到Python，请先安装Python 3.8+
    echo 下载地址: https://www.python.org/downloads/
    pause
    exit /b 1
)

echo 正在检查依赖...
pip show pytesseract >nul 2>&1
if errorlevel 1 (
    echo 正在安装Python依赖...
    pip install pytesseract pillow pygetwindow pandas openpyxl pywin32 -q
)

echo.
echo ========================================
echo     安装完成！
echo ========================================
echo.
echo 安装目录: %INSTALL_DIR%
echo.
echo 正在启动程序...
echo.
start "" "%INSTALL_DIR%\main.py"

timeout /t 3 >nul
exit