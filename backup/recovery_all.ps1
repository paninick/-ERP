# 全系统恢复脚本
# 执行所有恢复任务

# 配置参数
$backupDir = "D:\erp\backup"
$logFile = Join-Path $backupDir "logs" "recovery_$(Get-Date -Format 'yyyyMMdd').log"

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

# 启动服务
function Start-Service {
    param(
        [string]$ServiceName,
        [string]$DisplayName
    )
    Write-Log "启动$DisplayName服务"
    try {
        net start $ServiceName
        Write-Log "$DisplayName服务已启动"
        return $true
    } catch {
        Write-Log "启动$DisplayName服务失败: $($_.Exception.Message)" "ERROR"
        return $false
    }
}

# 检查服务状态
function Check-Service {
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

# 启动后端服务
function Start-BackendService {
    Write-Log "启动后端服务"
    try {
        # 启动后端服务
        $backendDir = "D:\erp\RuoYi-Vue\ruoyi-admin"
        Start-Process -WorkingDirectory $backendDir -FilePath "mvn" -ArgumentList "spring-boot:run" -WindowStyle Hidden
        Write-Log "后端服务已启动"
        return $true
    } catch {
        Write-Log "启动后端服务失败: $($_.Exception.Message)" "ERROR"
        return $false
    }
}

# 启动前端服务
function Start-FrontendService {
    Write-Log "启动前端服务"
    try {
        # 启动前端服务
        $frontendDir = "D:\erp\RuoYi-Vue\ruoyi-ui"
        Start-Process -WorkingDirectory $frontendDir -FilePath "npm" -ArgumentList "run dev" -WindowStyle Hidden
        Write-Log "前端服务已启动"
        return $true
    } catch {
        Write-Log "启动前端服务失败: $($_.Exception.Message)" "ERROR"
        return $false
    }
}

# 开始恢复
Write-Log "开始全系统恢复"

# 1. 检查服务状态
Write-Log "检查服务状态"
& "$backupDir\check_services.ps1"

# 2. 恢复数据库
Write-Log "执行数据库恢复"
try {
    & "$backupDir\recovery_mysql.ps1"
    Write-Log "数据库恢复完成"
} catch {
    Write-Log "数据库恢复失败: $($_.Exception.Message)" "ERROR"
}

# 3. 恢复配置文件
Write-Log "执行配置文件恢复"
try {
    & "$backupDir\recovery_config.ps1"
    Write-Log "配置文件恢复完成"
} catch {
    Write-Log "配置文件恢复失败: $($_.Exception.Message)" "ERROR"
}

# 4. 恢复上传文件
Write-Log "执行上传文件恢复"
try {
    & "$backupDir\recovery_upload.ps1"
    Write-Log "上传文件恢复完成"
} catch {
    Write-Log "上传文件恢复失败: $($_.Exception.Message)" "ERROR"
}

# 5. 启动服务
Write-Log "启动服务"

# 启动Redis服务
Start-Service -ServiceName "Redis" -DisplayName "Redis"

# 启动MySQL服务
Start-Service -ServiceName "MySQL80" -DisplayName "MySQL"

# 启动后端服务
Start-BackendService

# 启动前端服务
Start-FrontendService

# 6. 验证服务状态
Write-Log "验证服务状态"

# 等待服务启动
Write-Log "等待服务启动..."
Start-Sleep -Seconds 30

# 检查服务状态
Check-Service -Port 6379 -ServiceName "Redis"
Check-Service -Port 3306 -ServiceName "MySQL"
Check-Service -Port 8080 -ServiceName "后端服务"
Check-Service -Port 80 -ServiceName "前端服务"

Write-Log "全系统恢复完成"
