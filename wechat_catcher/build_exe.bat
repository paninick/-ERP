@echo off
chcp 65001 >nul
title 打包EXE安装包

echo ========================================
echo   微信群消息抓取 - EXE打包工具
echo ========================================
echo.

echo 正在安装打包工具...
pip install pyinstaller -q

echo.
echo 正在打包，请稍候...
echo.

pyinstaller main.py --name=微信群消息抓取 --onefile --windowed --clean --noconfirm

echo.
echo ========================================
echo   打包完成！
echo ========================================
echo.
echo 输出目录: dist\微信群消息抓取.exe
echo.
pause

start explorer dist