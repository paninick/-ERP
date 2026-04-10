# 上传文件备份脚本
# 备份用户上传的文件

# 配置参数
$backupDir = "D:\erp\backup\upload"
$uploadDirs = @(
    "D:\erp\RuoYi-Vue\ruoyi-admin\src\main\resources\static",
    "D:\erp\RuoYi-Vue\ruoyi-admin\target\classes\static"
)

# 创建备份目录（如果不存在）
if (-not (Test-Path $backupDir)) {
    New-Item -ItemType Directory -Path $backupDir -Force
}

# 执行备份
Write-Host "开始备份上传文件"
try {
    $backupName = "upload_backup_$(Get-Date -Format 'yyyyMMdd_HHmmss').zip"
    $backupPath = Join-Path $backupDir $backupName
    
    # 检查上传目录是否存在
    $existingDirs = @()
    foreach ($dir in $uploadDirs) {
        if (Test-Path $dir) {
            $existingDirs += $dir
        }
    }
    
    if ($existingDirs.Count -gt 0) {
        # 压缩上传文件
        Compress-Archive -Path $existingDirs -DestinationPath $backupPath -Force
        
        if (Test-Path $backupPath) {
            Write-Host "上传文件备份成功: $backupPath"
            
            # 清理7天前的备份
            $oldBackups = Get-ChildItem -Path $backupDir -Filter "*.zip" | Where-Object {$_.LastWriteTime -lt (Get-Date).AddDays(-7)}
            foreach ($backup in $oldBackups) {
                Remove-Item $backup.FullName -Force
                Write-Host "已清理过期备份: $($backup.Name)"
            }
        } else {
            Write-Host "上传文件备份失败" -ForegroundColor Red
        }
    } else {
        Write-Host "没有找到上传文件目录" -ForegroundColor Yellow
    }
} catch {
    Write-Host "备份过程中发生错误: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "备份任务完成"
