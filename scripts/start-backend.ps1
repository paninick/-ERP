param(
    [string]$JavaExe = "C:\Users\91306\AppData\Local\Programs\Microsoft\jdk-17.0.10.7-hotspot\bin\java.exe",
    [string]$JarPath = "D:\erp\RuoYi-Vue\ruoyi-admin\target\ruoyi-admin.jar",
    [string]$WorkDir = "D:\erp\RuoYi-Vue\ruoyi-admin\target"
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path $JavaExe)) {
    throw "Java runtime not found: $JavaExe"
}

if (-not (Test-Path $JarPath)) {
    throw "Backend jar not found: $JarPath"
}

$jarMatch = [regex]::Escape($JarPath)
$workDirMatch = [regex]::Escape($WorkDir)
$existingShells = Get-CimInstance Win32_Process -Filter "name = 'powershell.exe'" |
    Where-Object {
        $_.CommandLine -and
        $_.CommandLine -match $workDirMatch -and
        $_.CommandLine -match $jarMatch
    }

$existing = Get-CimInstance Win32_Process -Filter "name = 'java.exe'" |
    Where-Object { $_.CommandLine -and $_.CommandLine -match $jarMatch }

foreach ($proc in $existingShells) {
    try {
        Stop-Process -Id $proc.ProcessId -Force -ErrorAction Stop
        Write-Output "Stopped backend shell PID: $($proc.ProcessId)"
    } catch {
        Write-Output "Skipped exited backend shell PID: $($proc.ProcessId)"
    }
}

foreach ($proc in $existing) {
    try {
        Stop-Process -Id $proc.ProcessId -Force -ErrorAction Stop
        Write-Output "Stopped backend PID: $($proc.ProcessId)"
    } catch {
        Write-Output "Skipped exited backend PID: $($proc.ProcessId)"
    }
}

$command = "Set-Location '$WorkDir'; & '$JavaExe' -jar '$JarPath'"
$process = Start-Process -FilePath "powershell.exe" `
    -ArgumentList @("-NoProfile", "-NoExit", "-ExecutionPolicy", "Bypass", "-Command", $command) `
    -WindowStyle Minimized `
    -PassThru

Write-Output "Backend shell PID: $($process.Id)"
Write-Output "Waiting for http://localhost:8080 ..."

$listen = $null
for ($i = 0; $i -lt 60; $i++) {
    Start-Sleep -Seconds 1
    if ($process.HasExited) {
        throw "Backend shell exited early, startup failed. Check the minimized PowerShell window in $WorkDir."
    }
    $listen = netstat -ano | Select-String "^\s*TCP\s+\S+:8080\s+\S+\s+LISTENING\s+\d+$"
    if ($listen) {
        break
    }
}

if ($listen) {
    Write-Output "Backend ready on http://localhost:8080"
} else {
    Write-Output "Backend shell started, but port 8080 is not confirmed yet."
}
