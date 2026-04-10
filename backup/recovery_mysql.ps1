# MySQL数据库恢复脚本
# 从备份中恢复MySQL数据库

# 配置参数
$backupDir = "D:\erp\backup\database"
$mysqlBin = "C:\Program Files\MySQL\MySQL Server 8.0\bin"
$host = "localhost"
$port = "3306"
$user = "root"
$password = "123456"  # 请根据实际情况修改
$database = "ry-vue"
$logFile = "D:\erp\backup\logs\mysql_recovery_$(Get-Date -Format 'yyyyMMdd').log"

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

# 停止MySQL服务
function Stop-MySQLService {
    Write-Log "停止MySQL服务"
    try {
        net stop MySQL80  # 请根据实际服务名称修改
        Write-Log "MySQL服务已停止"
        return $true
    } catch {
        Write-Log "停止MySQL服务失败: $($_.Exception.Message)" "ERROR"
        return $false
    }
}

# 启动MySQL服务
function Start-MySQLService {
    Write-Log "启动MySQL服务"
    try {
        net start MySQL80  # 请根据实际服务名称修改
        Write-Log "MySQL服务已启动"
        return $true
    } catch {
        Write-Log "启动MySQL服务失败: $($_.Exception.Message)" "ERROR"
        return $false
    }
}

# 恢复数据库
function Restore-Database {
    param(
        [string]$BackupFile
    )
    Write-Log "开始恢复数据库: $BackupFile"
    try {
        # 解压缩备份文件
        if ($BackupFile -like "*.gz") {
            $tempFile = "$BackupFile.tmp.sql"
            Write-Log "解压缩备份文件"
            # 使用7-Zip解压缩（如果没有7-Zip，可以使用其他解压缩工具）
            if (Test-Path "C:\Program Files\7-Zip\7z.exe") {
                & "C:\Program Files\7-Zip\7z.exe" x $BackupFile -o"$backupDir" -y
                $BackupFile = $BackupFile -replace "\.gz$", ""
            } else {
                Write-Log "未找到7-Zip，请手动解压缩备份文件" "ERROR"
                return $false
            }
        }
        
        # 执行恢复
        Write-Log "执行数据库恢复"
        & "$mysqlBin\mysql.exe" --host=$host --port=$port --user=$user --password=$password -e "DROP DATABASE IF EXISTS $database; CREATE DATABASE $database CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
        & "$mysqlBin\mysql.exe" --host=$host --port=$port --user=$user --password=$password $database < $BackupFile
        
        if ($LASTEXITCODE -eq 0) {
            Write-Log "数据库恢复成功"
            return $true
        } else {
            Write-Log "数据库恢复失败" "ERROR"
            return $false
        }
    } catch {
        Write-Log "恢复过程中发生错误: $($_.Exception.Message)" "ERROR"
        return $false
    }
}

# 开始恢复
Write-Log "开始MySQL数据库恢复"

# 选择备份文件
$backupFiles = Get-ChildItem -Path $backupDir -Filter "*.sql.gz" | Sort-Object LastWriteTime -Descending
if ($backupFiles.Count -eq 0) {
    $backupFiles = Get-ChildItem -Path $backupDir -Filter "*.sql" | Sort-Object LastWriteTime -Descending
}

if ($backupFiles.Count -eq 0) {
    Write-Log "没有找到备份文件" "ERROR"
    exit 1
}

# 使用最新的备份文件
$latestBackup = $backupFiles[0]
Write-Log "使用最新的备份文件: $($latestBackup.Name)"

# 停止MySQL服务
if (Stop-MySQLService) {
    # 恢复数据库
    if (Restore-Database -BackupFile $latestBackup.FullName) {
        # 启动MySQL服务
        Start-MySQLService
    }
}

Write-Log "MySQL数据库恢复完成"
