# 配置文件备份脚本
# 备份系统配置文件和关键配置

# 配置参数
$backupDir = "D:\erp\backup\config"
$configDirs = @(
    "D:\erp\RuoYi-Vue\ruoyi-admin\src\main\resources",
    "D:\erp\RuoYi-Vue\ruoyi-ui\public",
    "D:\erp\RuoYi-Vue\ruoyi-ui\src\api",
    "D:\erp\RuoYi-Vue\ruoyi-ui\src\views"
)

# 创建备份目录（如果不存在）
if (-not (Test-Path $backupDir)) {
    New-Item -ItemType Directory -Path $backupDir -Force
}

# 执行备份
Write-Host "开始备份配置文件"
try {
    $backupName = "config_backup_$(Get-Date -Format 'yyyyMMdd_HHmmss').zip"
    $backupPath = Join-Path $backupDir $backupName
    
    # 压缩配置文件
    Compress-Archive -Path $configDirs -DestinationPath $backupPath -Force
    
    if (Test-Path $backupPath) {
        Write-Host "配置文件备份成功: $backupPath"
        
        # 清理7天前的备份
        $oldBackups = Get-ChildItem -Path $backupDir -Filter "*.zip" | Where-Object {$_.LastWriteTime -lt (Get-Date).AddDays(-7)}
        foreach ($backup in $oldBackups) {
            Remove-Item $backup.FullName -Force
            Write-Host "已清理过期备份: $($backup.Name)"
        }
    } else {
        Write-Host "配置文件备份失败" -ForegroundColor Red
    }
} catch {
    Write-Host "备份过程中发生错误: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "备份任务完成"
