#Requires -Version 5.1
<# Convert the saved-session artwork to a multi-resolution Windows ICO. #>
[CmdletBinding()]
param([string]$Source, [string]$Destination)
$ErrorActionPreference = 'Stop'
$root = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot '..')).Path
if (-not $Source) {
    $Source = Join-Path $root 'src\main\resources\icons\codetrace-session.png'
}
if (-not $Destination) { $Destination = Join-Path $root 'target\codetrace-session.ico' }
if (-not (Test-Path -LiteralPath $Source -PathType Leaf)) {
    throw "No se encuentra la imagen de sesión: $Source"
}
Add-Type -AssemblyName System.Drawing
$image = [System.Drawing.Image]::FromFile($Source)
$images = @()
try {
    foreach ($size in @(16, 24, 32, 48, 64, 128, 256)) {
        $bitmap = [System.Drawing.Bitmap]::new(
            $size, $size, [System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
        $graphics = [System.Drawing.Graphics]::FromImage($bitmap)
        try {
            $graphics.Clear([System.Drawing.Color]::Transparent)
            $graphics.InterpolationMode =
                [System.Drawing.Drawing2D.InterpolationMode]::HighQualityBicubic
            $graphics.SmoothingMode =
                [System.Drawing.Drawing2D.SmoothingMode]::HighQuality
            $graphics.DrawImage($image, 0, 0, $size, $size)
            $memory = [System.IO.MemoryStream]::new()
            try {
                $bitmap.Save($memory, [System.Drawing.Imaging.ImageFormat]::Png)
                $images += ,([pscustomobject]@{ Size=$size; Bytes=$memory.ToArray() })
            } finally { $memory.Dispose() }
        } finally {
            $graphics.Dispose()
            $bitmap.Dispose()
        }
    }
} finally { $image.Dispose() }

$directory = Split-Path -Parent $Destination
if (-not (Test-Path -LiteralPath $directory)) {
    New-Item -ItemType Directory -Path $directory -Force | Out-Null
}
$memory = [System.IO.MemoryStream]::new()
$writer = [System.IO.BinaryWriter]::new($memory)
try {
    $writer.Write([uint16]0)  # Reserved
    $writer.Write([uint16]1)  # Icon
    $writer.Write([uint16]$images.Count)
    $offset = 6 + 16 * $images.Count
    foreach ($entry in $images) {
        $dimension = if ($entry.Size -eq 256) { 0 } else { $entry.Size }
        $writer.Write([byte]$dimension)
        $writer.Write([byte]$dimension)
        $writer.Write([byte]0) # Colors
        $writer.Write([byte]0) # Reserved
        $writer.Write([uint16]1)
        $writer.Write([uint16]32)
        $writer.Write([uint32]$entry.Bytes.Length)
        $writer.Write([uint32]$offset)
        $offset += $entry.Bytes.Length
    }
    foreach ($entry in $images) { $writer.Write([byte[]]$entry.Bytes) }
    $writer.Flush()
    [System.IO.File]::WriteAllBytes($Destination, $memory.ToArray())
} finally {
    $writer.Dispose()
    $memory.Dispose()
}
Write-Host "Icono de sesion preparado: $Destination"
