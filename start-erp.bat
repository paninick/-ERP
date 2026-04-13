@echo off
chcp 65001 >nul
echo ========================================
echo   ERP 系统启动脚本
echo ========================================
echo.

cd /d "%~dp0"

echo [1/2] 启动后端服务...
start "RuoYi-Backend" cmd /k "cd /d "%~dp0ruoyi-admin\target" && java -jar -Xms256m -Xmx1024m ruoyi-admin.jar"

timeout /t 15 /nobreak >nul

echo [2/2] 启动前端服务...
start "RuoYi-Frontend" cmd /k "cd /d "%~dp0ruoyi-ui" && npm run dev"

echo.
echo ========================================
echo   ERP 启动完成！
echo   后端: http://localhost:8080
echo   前端: http://localhost:80
echo ========================================
pause