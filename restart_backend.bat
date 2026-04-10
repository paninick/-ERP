
@echo off
chcp 65001
echo ========================================
echo 正在重启后端服务...
echo ========================================

cd /d d:\erp\RuoYi-Vue

echo.
echo 1. 停止当前运行的Java进程...
taskkill /F /IM java.exe 2&gt;nul
timeout /t 3 /nobreak &gt;nul

echo.
echo 2. 启动后端服务...
cd ruoyi-admin\target
start "RuoYi-Backend" java -jar -Xms256m -Xmx1024m ruoyi-admin.jar

echo.
echo ========================================
echo 后端服务启动中...
echo 请等待15-30秒后刷新浏览器
echo ========================================
timeout /t 20 /nobreak
echo.
echo 后端服务已启动！
echo.
echo 请在浏览器中按 Ctrl + F5 硬刷新页面！
pause

