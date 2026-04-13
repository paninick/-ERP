@echo off
set JAVA="C:\Users\91306\AppData\Local\Programs\Microsoft\jdk-17.0.10.7-hotspot\bin\java.exe"
set JAR="D:\erp\RuoYi-Vue\ruoyi-admin\target\ruoyi-admin.jar"
cd /d D:\erp\RuoYi-Vue\ruoyi-admin
%JAVA% -jar %JAR%
pause
