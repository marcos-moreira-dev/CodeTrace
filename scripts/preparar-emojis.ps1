#Requires -Version 5.1
<#
Fetch a pinned, official Twemoji archive once and extract ONLY SVGs listed
in the committed manifest. All images are bundled into CodeTrace; nothing
is fetched when a Markdown document is displayed. Requires CC-BY-4.0
attribution, included in the app repository and installation.
#>
[CmdletBinding()]
param([switch]$Required)
$ErrorActionPreference = "Stop"
$root = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot "..")).Path
$folder = Join-Path $root "src\main\resources\emojis\twemoji"
$manifest = Join-Path $folder "manifest.txt"
if (-not (Test-Path -LiteralPath $manifest)) {
    if ($Required) { throw "Falta el catálogo de emojis." }
    Write-Warning "Catálogo de emojis no disponible."
    return
}
$names = @(Get-Content -LiteralPath $manifest -Encoding UTF8 |
    Where-Object { $_ -match '^[0-9a-f]+(?:-[0-9a-f]+)*$' })
$wanted = @{}
foreach ($name in $names) {
    if (-not (Test-Path -LiteralPath (Join-Path $folder "$name.svg"))) {
        $wanted[$name] = $true
    }
}
if ($wanted.Count -eq 0) {
    Write-Host "Twemoji: $($names.Count) recursos SVG locales listos."
    return
}
$upstream = "b6b55fef1e8636b540a6d016a4729ca8cdf2e60b"
$url = "https://codeload.github.com/jdecked/twemoji/zip/$upstream"
$temp = Join-Path $env:TEMP ("codetrace-twemoji-" + [guid]::NewGuid().ToString("N") + ".zip")
$downloaded = 0
try {
    Write-Host "Preparando $($wanted.Count) emojis vectoriales desde Twemoji (CC BY 4.0)..."
    [Net.ServicePointManager]::SecurityProtocol =
        [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12
    Invoke-WebRequest -Uri $url -OutFile $temp -UseBasicParsing
    Add-Type -AssemblyName System.IO.Compression.FileSystem
    $zip = [System.IO.Compression.ZipFile]::OpenRead($temp)
    try {
        foreach ($entry in $zip.Entries) {
            if ($entry.FullName -notmatch '/assets/svg/([0-9a-f-]+)\.svg$') { continue }
            $name = [string]$Matches[1]
            if (-not $wanted.ContainsKey($name)) { continue }
            $path = Join-Path $folder "$name.svg"
            $inputStream = $entry.Open()
            $outputStream = [System.IO.File]::Create($path)
            try { $inputStream.CopyTo($outputStream) }
            finally { $outputStream.Dispose(); $inputStream.Dispose() }
            $downloaded++
        }
    } finally { $zip.Dispose() }
} catch {
    Write-Warning ("No se pudo descargar el paquete gráfico: " + $_.Exception.Message)
    if ($Required) { throw }
} finally {
    Remove-Item -LiteralPath $temp -Force -ErrorAction SilentlyContinue
}
$missing = @($names | Where-Object {
    -not (Test-Path -LiteralPath (Join-Path $folder "$_.svg"))
})
Write-Host "Twemoji: $($names.Count - $missing.Count)/$($names.Count) gráficos disponibles."
if ($missing.Count -gt 0 -and $Required) {
    throw "$($missing.Count) emojis vectoriales siguen sin estar disponibles."
}
# Bundled glyphs in the repository remain a safe fallback for offline builds.
