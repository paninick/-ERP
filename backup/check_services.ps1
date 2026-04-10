# 服务状态检查脚本
# 检查系统服务的运行状态

# 配置参数
$logFile = "D:\erp\backup\logs\service_check_$(Get-Date -Format 'yyyyMMdd').log"

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

# 检查端口状态
function Check-Port {
    param(
        [int]$Port,
        [string]$ServiceName
    )
    $result = netstat -ano | findstr ":$Port"
    if ($result) {
        Write-Log "$ServiceName 服务运行正常（端口 $Port）" "INFO"
        return $true
    } else {
        Write-Log "$ServiceName 服务未运行（端口 $Port）" "ERROR"
        return $false
    }
}

# 检查进程状态
function Check-Process {
    param(
        [string]$ProcessName,
        [string]$ServiceName
    )
    $process = Get-Process -Name $ProcessName -ErrorAction SilentlyContinue
    if ($process) {
        Write-Log "$ServiceName 进程运行正常" "INFO"
        return $true
    } else {
        Write-Log "$ServiceName 进程未运行" "ERROR"
        return $false
    }
}

# 开始检查
Write-Log "开始检查服务状态"

# 检查Redis服务
Check-Port -Port 6379 -ServiceName "Redis"

# 检查MySQL服务
Check-Port -Port 3306 -ServiceName "MySQL"

# 检查后端服务
Check-Port -Port 8080 -ServiceName "后端服务"

# 检查前端服务
Check-Port -Port 80 -ServiceName "前端服务"

Write-Log "服务状态检查完成"
