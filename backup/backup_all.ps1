# 全系统备份脚本
# 执行所有备份任务

# 配置参数
$backupDir = "D:\erp\backup"
$logFile = Join-Path $backupDir "logs" "backup_$(Get-Date -Format 'yyyyMMdd').log"

# 创建日志目录（如果不存在）
$logDir = Join-Path $backupDir "logs"
if (-not (Test-Path $logDir)) {
    New-Item -ItemType Directory -Path $logDir -Force
}

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

# 开始备份
Write-Log "开始全系统备份"

# 执行配置文件备份
Write-Log "执行配置文件备份"
try {
    & "$backupDir\backup_config.ps1"
    Write-Log "配置文件备份完成"
} catch {
    Write-Log "配置文件备份失败: $($_.Exception.Message)" "ERROR"
}

# 执行上传文件备份
Write-Log "执行上传文件备份"
try {
    & "$backupDir\backup_upload.ps1"
    Write-Log "上传文件备份完成"
} catch {
    Write-Log "上传文件备份失败: $($_.Exception.Message)" "ERROR"
}

# 执行数据库备份
Write-Log "执行数据库备份"
try {
    & "$backupDir\backup_mysql.ps1"
    Write-Log "数据库备份完成"
} catch {
    Write-Log "数据库备份失败: $($_.Exception.Message)" "ERROR"
}

Write-Log "全系统备份完成"
