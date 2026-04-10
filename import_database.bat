@echo off
cd /d C:\Program Files\MySQL\MySQL Server 8.4\bin
mysql -u root -ppassword ry-vue < d:\erp\RuoYi-Vue\sql\ry_20260321.sql
echo Done!
pause
