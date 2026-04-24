param(
    [string]$JarPath = "D:\erp\RuoYi-Vue\ruoyi-admin\target\ruoyi-admin.jar"
)

$ErrorActionPreference = "Stop"

$jarMatch = [regex]::Escape($JarPath)
$workDir = Split-Path $JarPath -Parent
$workDirMatch = [regex]::Escape($workDir)
$existingShells = Get-CimInstance Win32_Process -Filter "name = 'powershell.exe'" |
    Where-Object {
        $_.CommandLine -and
        $_.CommandLine -match $workDirMatch -and
        $_.CommandLine -match $jarMatch
    }

$existing = Get-CimInstance Win32_Process -Filter "name = 'java.exe'" |
    Where-Object { $_.CommandLine -and $_.CommandLine -match $jarMatch }

if (-not $existing -and -not $existingShells) {
    Write-Output "No backend process found."
    exit 0
}

foreach ($proc in $existing) {
    try {
        Stop-Process -Id $proc.ProcessId -Force -ErrorAction Stop
        Write-Output "Stopped backend PID: $($proc.ProcessId)"
    } catch {
        Write-Output "Backend PID already exited: $($proc.ProcessId)"
    }
}

foreach ($proc in $existingShells) {
    try {
        Stop-Process -Id $proc.ProcessId -Force -ErrorAction Stop
        Write-Output "Stopped backend shell PID: $($proc.ProcessId)"
    } catch {
        Write-Output "Backend shell PID already exited: $($proc.ProcessId)"
    }
}

Start-Sleep -Seconds 2

$listen = netstat -ano | Select-String "^\s*TCP\s+\S+:8080\s+\S+\s+LISTENING\s+\d+$"
if ($listen) {
    Write-Output "Port 8080 still has listeners:"
    $listen | ForEach-Object { Write-Output $_.ToString() }
} else {
    Write-Output "Backend port 8080 released."
}
