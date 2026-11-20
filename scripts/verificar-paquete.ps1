#Requires -Version 5.1
<#
.SYNOPSIS
  Verifies a Windows CodeTrace portable image and its ZIP before publication.
.DESCRIPTION
  Static verification only: it does not start an interactive JavaFX process
  in a headless CI runner. A separate manual test on Windows is still required.
#>
[CmdletBinding()]
param(
    [string]$ImagePath = "dist/CodeTrace",
    [string]$ZipPath = "dist/CodeTrace-Windows-portable.zip"
)
$ErrorActionPreference = "Stop"
$root = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
function Resolve-InRepository([string]$target) {
    if ([IO.Path]::IsPathRooted($target)) { return $target }
    return Join-Path $root $target
}
$image = Resolve-InRepository $ImagePath
$zip = Resolve-InRepository $ZipPath
$executable = Join-Path $image "CodeTrace.exe"
$runtime = Join-Path $image "runtime"
$app = Join-Path $image "app"
foreach ($required in @($executable, $runtime, $app, $zip)) {
    if (-not (Test-Path -LiteralPath $required)) {
        throw "Falta un archivo o directorio del paquete: $required"
    }
}
$jar = @(Get-ChildItem -LiteralPath $app -Filter "codetrace-*.jar" -File)
if ($jar.Count -lt 1) { throw "El paquete no contiene el JAR de CodeTrace." }
foreach ($legal in @("CodeTrace-MIT.txt", "LICENSING.md", "THIRD-PARTY-NOTICES.md", "IBM-Plex-OFL.txt", "Twemoji-CC-BY-4.0.txt")) {
    if (-not (Test-Path -LiteralPath (Join-Path (Join-Path $app "licenses") $legal) -PathType Leaf)) {
        throw "No se incluyó el aviso legal requerido: $legal"
    }
}
$zipInfo = Get-Item -LiteralPath $zip
if ($zipInfo.Length -lt 1048576) {
    throw "ZIP portable demasiado pequeño para contener un runtime Java."
}

Add-Type -AssemblyName System.IO.Compression.FileSystem
$archive = [IO.Compression.ZipFile]::OpenRead($zip)
try {
    $entries = @($archive.Entries | ForEach-Object {
        $_.FullName.Replace('\', '/')
    })
    foreach ($suffix in @("/CodeTrace.exe", "/app/CodeTrace.cfg")) {
        if (-not @($entries | Where-Object { $_.EndsWith($suffix) }).Count) {
            throw "Falta '$suffix' dentro del ZIP."
        }
    }
    if (-not @($entries | Where-Object { $_ -like "*/runtime/*" }).Count) {
        throw "El ZIP no contiene el runtime Java de CodeTrace."
    }
    if (-not @($entries | Where-Object { $_ -like "*/app/codetrace-*.jar" }).Count) {
        throw "El ZIP no contiene el artefacto compilado."
    }
    foreach ($legal in @("CodeTrace-MIT.txt", "LICENSING.md", "THIRD-PARTY-NOTICES.md", "IBM-Plex-OFL.txt", "Twemoji-CC-BY-4.0.txt")) {
        if (-not @($entries | Where-Object { $_.EndsWith("/app/licenses/$legal") }).Count) {
            throw "El ZIP no contiene el aviso legal requerido: $legal"
        }
    }
} finally {
    $archive.Dispose()
}

$hashFile = "$zip.sha256"
if (-not (Test-Path -LiteralPath $hashFile)) {
    throw "Falta el archivo de integridad $hashFile"
}
$expected = ([IO.File]::ReadAllText($hashFile) -split '\s+')[0].Trim()
$calculated = (Get-FileHash -LiteralPath $zip -Algorithm SHA256).Hash
if ($expected -notmatch '^[a-fA-F0-9]{64}$' -or
        -not $expected.Equals($calculated, [StringComparison]::OrdinalIgnoreCase)) {
    throw "La huella SHA-256 del ZIP no coincide."
}
Write-Host "Paquete CodeTrace correcto: app, runtime, JAR y ZIP."
Write-Host "SHA-256: $calculated"
Write-Host "Verificación estructural aprobada; falta iniciar el EXE en Windows real."
