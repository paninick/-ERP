# MySQL数据库备份脚本
# 定期备份MySQL数据库，支持自动清理过期备份

# 配置参数
$backupDir = "D:\erp\backup\database"
$mysqlBin = "C:\Program Files\MySQL\MySQL Server 8.0\bin"
$host = "localhost"
$port = "3306"
$user = "root"
$password = "123456"  # 请根据实际情况修改
$database = "ry-vue"
$backupName = "mysql_backup_$(Get-Date -Format 'yyyyMMdd_HHmmss').sql"
$backupPath = Join-Path $backupDir $backupName

# 创建备份目录（如果不存在）
if (-not (Test-Path $backupDir)) {
    New-Item -ItemType Directory -Path $backupDir -Force
}

# 执行备份
Write-Host "开始备份数据库: $database"
try {
    # 使用mysqldump进行备份
    & "$mysqlBin\mysqldump.exe" --host=$host --port=$port --user=$user --password=$password --databases $database --single-transaction --routines --triggers --events > $backupPath
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "数据库备份成功: $backupPath"
        
        # 压缩备份文件
        $zipPath = "$backupPath.gz"
        Compress-Archive -Path $backupPath -DestinationPath $zipPath -Force
        Remove-Item $backupPath -Force
        Write-Host "备份文件已压缩: $zipPath"
        
        # 清理7天前的备份
        $oldBackups = Get-ChildItem -Path $backupDir -Filter "*.gz" | Where-Object {$_.LastWriteTime -lt (Get-Date).AddDays(-7)}
        foreach ($backup in $oldBackups) {
            Remove-Item $backup.FullName -Force
            Write-Host "已清理过期备份: $($backup.Name)"
        }
    } else {
        Write-Host "数据库备份失败" -ForegroundColor Red
    }
} catch {
    Write-Host "备份过程中发生错误: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "备份任务完成"
