#Requires -Version 5.1
# Automatic, local-only screenshot sampling of REAL CodeTrace windows.
# It records significant visual changes while a person navigates the app.
# It does NOT simulate keyboard input, change the project, or upload images.
[CmdletBinding()]
param(
  [ValidateRange(30,3600)][int]$DurationSeconds = 300,
  [ValidateRange(10,250)][int]$MaxScreenshots = 100,
  [switch]$NoExplorer
)
$ErrorActionPreference = "Stop"
Add-Type -AssemblyName System.Drawing
$root = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot "..")).Path
$stamp = Get-Date -Format "yyyyMMdd-HHmmss"
$reports = Join-Path $root "reports"
$work = Join-Path $reports ("CodeTrace-auto-UX-" + $stamp)
$screenFolder = Join-Path $work "screenshots"
$zip = Join-Path $reports ("CodeTrace-auditoria-UX-auto-" + $stamp + ".zip")
New-Item -ItemType Directory -Force -Path $screenFolder | Out-Null
$capturer = Join-Path $PSScriptRoot "capturar-ventana.ps1"
$ps = Join-Path $env:SystemRoot "System32\WindowsPowerShell\v1.0\powershell.exe"
$manifest = New-Object "System.Collections.Generic.List[string]"
$manifest.Add("CodeTrace - Capturas automaticas de interfaz")
$manifest.Add("Fecha: " + (Get-Date -Format "yyyy-MM-dd HH:mm:ss"))
$manifest.Add("Solo ventanas del proceso CodeTrace verificado.")
$manifest.Add("No se navega automaticamente ni se envia nada a Internet.")
$manifest.Add("Revisa rutas y datos privados antes de compartir.")
$manifest.Add("")

function Signature([string]$file) {
    $bitmap = [System.Drawing.Bitmap]::FromFile($file)
    try {
        $values = New-Object "System.Collections.Generic.List[int]"
        for ($y = 0; $y -lt 24; $y++) {
            for ($x = 0; $x -lt 40; $x++) {
                $px = $bitmap.GetPixel(
                    [Math]::Min($bitmap.Width - 1,
                        [int](($x + 0.5) * $bitmap.Width / 40)),
                    [Math]::Min($bitmap.Height - 1,
                        [int](($y + 0.5) * $bitmap.Height / 24)))
                $values.Add((($px.R -shr 4) -shl 8) -bor
                    (($px.G -shr 4) -shl 4) -bor ($px.B -shr 4))
            }
        }
        return ,($values.ToArray())
    } finally { $bitmap.Dispose() }
}
function Difference([int[]]$a, [int[]]$b) {
    if ($null -eq $a -or $null -eq $b -or $a.Length -ne $b.Length) {
        return 1.0
    }
    $changed = 0
    for ($i = 0; $i -lt $a.Length; $i++) {
        if ($a[$i] -ne $b[$i]) { $changed++ }
    }
    return [double]$changed / [double]$a.Length
}

Write-Host "CodeTrace: auditoria visual automatica" -ForegroundColor Cyan
Write-Host "Abre CodeTrace y navega normalmente. Se capturan cambios grandes de la UI."
Write-Host "Cierra la app para terminar. No se captura la consola ni otras aplicaciones."
Write-Host "No se carga nada a Internet."
$start = Get-Date
$limit = $start.AddSeconds($DurationSeconds)
$count = 0
$hasSeen = $false
$absent = 0
$previous = $null
$lastScreenshot = [datetime]::MinValue
$lastFailure = ""
try {
    while ((Get-Date) -lt $limit -and $count -lt $MaxScreenshots) {
        $candidate = Join-Path $work "candidate.png"
        $details = ""
        try {
            $details = (& $ps -NoProfile -ExecutionPolicy Bypass -File $capturer -OutputFile $candidate -TimeoutSeconds 0 2>&1 | Out-String)
        } catch { $details = $_.Exception.Message }
        if (Test-Path -LiteralPath $candidate) {
            $hasSeen = $true
            $absent = 0
            try {
                $visual = Signature $candidate
                $now = Get-Date
                $ratio = Difference $previous $visual
                if ($count -eq 0 -or
                    ($ratio -gt 0.045 -and ($now - $lastScreenshot).TotalMilliseconds -ge 1200)) {
                    $count++
                    $dest = Join-Path $screenFolder ("captura-{0:D3}.png" -f $count)
                    Move-Item -LiteralPath $candidate -Destination $dest -Force
                    $previous = $visual
                    $lastScreenshot = $now
                    $title = ($details -split "\r?\n" | Where-Object { $_ -match "^VENTANA:" } | Select-Object -First 1)
                    $manifest.Add(("{0:D3} | {1:HH:mm:ss} | {2}" -f $count, $now, $title))
                    Write-Host ("Captura {0:D3}" -f $count) -ForegroundColor Green
                }
            } finally {
                if (Test-Path -LiteralPath $candidate) {
                    Remove-Item -LiteralPath $candidate -Force
                }
            }
        } else {
            $absent++
            $lastFailure = $details.Trim()
            # Ignore temporary app switching; stop after app stays out of focus.
            if ($hasSeen -and $absent -ge 10) { break }
            if (-not $hasSeen -and (Get-Date) -ge $start.AddSeconds(35)) {
                Write-Warning "No se encontro CodeTrace en primer plano."
                break
            }
        }
        Start-Sleep -Milliseconds 1000
    }
} finally {
    $manifest.Add("")
    $manifest.Add("Total: " + $count)
    if ($count -eq 0) { $manifest.Add("Diagnostico: " + $lastFailure) }
    $manifest.Add("Solo se capturaron pantallas mostradas por el usuario.")
    $manifest.Add("Para 37 casos concretos, usar -Guiado o navegar por todos.")
    if (Test-Path -LiteralPath (Join-Path $work "candidate.png")) {
        Remove-Item -LiteralPath (Join-Path $work "candidate.png") -Force
    }
    [System.IO.File]::WriteAllLines(
        (Join-Path $work "INDICE-Y-PRIVACIDAD.txt"), [string[]]$manifest,
        [System.Text.UTF8Encoding]::new($true))
    Compress-Archive -Path (Join-Path $work "*") -DestinationPath $zip -Force
    Write-Host ("ZIP: " + $zip) -ForegroundColor Cyan
    Write-Host "Revisa las capturas antes de compartir."
    if (-not $NoExplorer) {
        Invoke-Item -LiteralPath $reports
    }
}
