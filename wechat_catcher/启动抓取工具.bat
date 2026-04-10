@echo off
chcp 65001 >nul
echo ======================================
echo   微信群聊消息抓取工具 v2
echo ======================================
echo.
echo 正在启动GUI界面...
echo.
python "%~dp0wechat_catcher_v2.py"
pause