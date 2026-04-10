# 配置文件恢复脚本
# 从备份中恢复配置文件

# 配置参数
$backupDir = "D:\erp\backup\config"
$targetDir = "D:\erp\RuoYi-Vue"
$logFile = "D:\erp\backup\logs\config_recovery_$(Get-Date -Format 'yyyyMMdd').log"

# 写入日志
function Write-Log {
    param(
        [string]$Message,
        [string]$Level = "INFO"
    )
    $timestamp = Get-Date -Format 'yyyy-MM-dd HH:mm:ss'
    $logMessage = "[$timestamp] [$Level] $Message"
    Write-Host $logMessage
    Add-Content -Path $logFile -Value $logMessage
}

# 恢复配置文件
function Restore-Config {
    param(
        [string]$BackupFile
    )
    Write-Log "开始恢复配置文件: $BackupFile"
    try {
        # 创建临时目录
        $tempDir = "$backupDir\temp"
        if (Test-Path $tempDir) {
            Remove-Item -Path $tempDir -Recurse -Force
        }
        New-Item -ItemType Directory -Path $tempDir -Force
        
        # 解压缩备份文件
        Write-Log "解压缩备份文件"
        Expand-Archive -Path $BackupFile -DestinationPath $tempDir -Force
        
        # 复制配置文件
        Write-Log "复制配置文件"
        $backupItems = Get-ChildItem -Path $tempDir -Recurse
        foreach ($item in $backupItems) {
            if ($item.PSIsContainer) {
                continue
            }
            
            # 计算目标路径
            $relativePath = $item.FullName.Substring($tempDir.Length)
            $targetPath = Join-Path $targetDir $relativePath
            
            # 创建目标目录
            $targetParentDir = Split-Path $targetPath -Parent
            if (-not (Test-Path $targetParentDir)) {
                New-Item -ItemType Directory -Path $targetParentDir -Force
            }
            
            # 复制文件
            Copy-Item -Path $item.FullName -Destination $targetPath -Force
            Write-Log "已恢复配置文件: $relativePath"
        }
        
        # 清理临时目录
        Remove-Item -Path $tempDir -Recurse -Force
        
        Write-Log "配置文件恢复成功"
        return $true
    } catch {
        Write-Log "恢复过程中发生错误: $($_.Exception.Message)" "ERROR"
        return $false
    }
}

# 开始恢复
Write-Log "开始配置文件恢复"

# 选择备份文件
$backupFiles = Get-ChildItem -Path $backupDir -Filter "*.zip" | Sort-Object LastWriteTime -Descending

if ($backupFiles.Count -eq 0) {
    Write-Log "没有找到备份文件" "ERROR"
    exit 1
}

# 使用最新的备份文件
$latestBackup = $backupFiles[0]
Write-Log "使用最新的备份文件: $($latestBackup.Name)"

# 恢复配置文件
Restore-Config -BackupFile $latestBackup.FullName

Write-Log "配置文件恢复完成"
