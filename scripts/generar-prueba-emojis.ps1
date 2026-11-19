#Requires -Version 5.1
<#
  Copia ambas pruebas Unicode/emoji desde el repositorio a Descargas.
  Nunca modifica archivos originales del proyecto ni sobrescribe una prueba
  anterior con el mismo nombre.
#>
[CmdletBinding()]
param()
$ErrorActionPreference = 'Stop'
$repo = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot '..')).Path
$src = Join-Path $repo 'samples\emojis'
$downloads = Join-Path $env:USERPROFILE 'Downloads'
if (-not (Test-Path -LiteralPath $downloads -PathType Container)) {
    throw "No se encuentra Descargas: $downloads. Guarda los archivos manualmente."
}
foreach ($name in @('CodeTrace-Emojis-Prueba.txt','CodeTrace-Emojis-Prueba.md')) {
    $from = Join-Path $src $name
    if (-not (Test-Path -LiteralPath $from -PathType Leaf)) {
        throw "No se encuentra la plantilla de prueba: $from"
    }
    $out = Join-Path $downloads $name
    if (Test-Path -LiteralPath $out) {
        $timestamp = Get-Date -Format 'yyyyMMdd-HHmmss'
        $stem = [System.IO.Path]::GetFileNameWithoutExtension($name)
        $extension = [System.IO.Path]::GetExtension($name)
        $out = Join-Path $downloads "$stem-$timestamp$extension"
    }
    Copy-Item -LiteralPath $from -Destination $out -ErrorAction Stop
    Write-Host "Generado: $out" -ForegroundColor Green
}
Write-Host 'Abre el TXT en consulta y el Markdown en Fuente/Renderizado.' -ForegroundColor Cyan
