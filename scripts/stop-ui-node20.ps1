param(
    [string]$UiDir = "D:\erp\RuoYi-Vue\ruoyi-ui"
)

$ErrorActionPreference = "Stop"

$repoMatch = [regex]::Escape($UiDir)
$cliMatch = [regex]::Escape("vue-cli-service.js")

$existingShells = Get-CimInstance Win32_Process -Filter "name = 'powershell.exe'" |
    Where-Object {
        $_.CommandLine -and
        $_.CommandLine -match $repoMatch -and
        (
            $_.CommandLine -match "npm run dev" -or
            $_.CommandLine -match $cliMatch
        )
    }

$existing = Get-CimInstance Win32_Process -Filter "name = 'node.exe'" |
    Where-Object {
        $_.CommandLine -and
        $_.CommandLine -match $repoMatch -and
        $_.CommandLine -match $cliMatch
    }

if (-not $existing -and -not $existingShells) {
    Write-Output "No UI Node20 process found."
    exit 0
}

foreach ($proc in $existing) {
    try {
        Stop-Process -Id $proc.ProcessId -Force -ErrorAction Stop
        Write-Output "Stopped UI PID: $($proc.ProcessId)"
    } catch {
        Write-Output "UI PID already exited: $($proc.ProcessId)"
    }
}

foreach ($proc in $existingShells) {
    try {
        Stop-Process -Id $proc.ProcessId -Force -ErrorAction Stop
        Write-Output "Stopped UI shell PID: $($proc.ProcessId)"
    } catch {
        Write-Output "UI shell PID already exited: $($proc.ProcessId)"
    }
}

Start-Sleep -Seconds 2

$listen = netstat -ano | Select-String "^\s*TCP\s+\S+:80\s+\S+\s+LISTENING\s+\d+$"
if ($listen) {
    Write-Output "Port 80 still has listeners:"
    $listen | ForEach-Object { Write-Output $_.ToString() }
} else {
    Write-Output "UI port 80 released."
}
