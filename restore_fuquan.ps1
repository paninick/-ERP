# 恢复富泉工贸架构数据 - PowerShell 脚本
# 确保以正确的字符集执行

$mysqlPath = "C:\xampp\mysql\bin\mysql.exe"
$sqlFile = "D:\erp\restore_fuquan_fixed.sql"
$dbName = "ry-vue"
$username = "root"

# 设置控制台输出编码
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$env:MYSQL_PWD = ""

# 执行 SQL 脚本
Write-Host "正在执行数据库恢复脚本..."
& $mysqlPath --default-character-set=utf8mb4 -u $username $dbName < $sqlFile

if ($LASTEXITCODE -eq 0) {
    Write-Host "数据库恢复成功！" -ForegroundColor Green
    
    # 验证数据
    Write-Host "`n验证数据..."
    & $mysqlPath --default-character-set=utf8mb4 -u $username $dbName -e "SELECT '部门数据' AS type, COUNT(*) AS count FROM sys_dept; SELECT '角色数据' AS type, COUNT(*) AS count FROM sys_role; SELECT '菜单数据' AS type, COUNT(*) AS count FROM sys_menu; SELECT '菜单示例' AS info, menu_id, menu_name FROM sys_menu LIMIT 5;"
} else {
    Write-Host "数据库恢复失败！" -ForegroundColor Red
    exit 1
}
