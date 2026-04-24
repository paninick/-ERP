param(
    [string]$NodeHome = "D:\erp\tools\node20",
    [string]$UiDir = "D:\erp\RuoYi-Vue\ruoyi-ui"
)

$ErrorActionPreference = "Stop"

$nodeExe = Join-Path $NodeHome "node.exe"
$cliScript = Join-Path $UiDir "node_modules\@vue\cli-service\bin\vue-cli-service.js"
$stamp = Get-Date -Format "yyyyMMdd-HHmmss"
$outLog = Join-Path $UiDir ("codex-ui-node20-" + $stamp + ".out.log")
$errLog = Join-Path $UiDir ("codex-ui-node20-" + $stamp + ".err.log")

if (-not (Test-Path $nodeExe)) {
    throw "Node20 runtime not found: $nodeExe"
}

if (-not (Test-Path $cliScript)) {
    throw "Vue CLI entry not found: $cliScript"
}

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

foreach ($proc in $existingShells) {
    try {
        Stop-Process -Id $proc.ProcessId -Force -ErrorAction Stop
    } catch {
        Write-Output "Skipped exited UI shell PID: $($proc.ProcessId)"
    }
}

foreach ($proc in $existing) {
    try {
        Stop-Process -Id $proc.ProcessId -Force -ErrorAction Stop
    } catch {
        Write-Output "Skipped exited UI PID: $($proc.ProcessId)"
    }
}

Start-Sleep -Seconds 2

$command = "Set-Location '$UiDir'; & '$nodeExe' '$cliScript' serve"
$process = Start-Process -FilePath "powershell.exe" `
    -ArgumentList @("-NoProfile", "-NoExit", "-ExecutionPolicy", "Bypass", "-Command", $command) `
    -WindowStyle Minimized `
    -PassThru

$listen = $null
for ($i = 0; $i -lt 60; $i++) {
    Start-Sleep -Seconds 1
    if ($process.HasExited) {
        throw "UI shell exited early, startup failed. Check the minimized PowerShell window in $UiDir."
    }
    $listen = netstat -ano | Select-String "^\s*TCP\s+\S+:80\s+\S+\s+LISTENING\s+\d+$"
    if ($listen) {
        break
    }
}

Write-Output "UI shell PID: $($process.Id)"
if ($listen) {
    Write-Output "UI ready on http://localhost:80"
} else {
    Write-Output "UI shell started, but port 80 is not confirmed yet. Check:"
    Write-Output "  $outLog"
    Write-Output "  $errLog"
}
