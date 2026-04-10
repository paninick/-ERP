@echo off
chcp 65001 >nul
title 微信群消息抓取

echo 检查并安装依赖...
pip install wxauto4 pandas openpyxl pygetwindow Pillow pytesseract -q 2>nul

python "%~dp0main.py"
pause