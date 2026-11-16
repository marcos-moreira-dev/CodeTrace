#Requires -Version 5.1
[CmdletBinding()]
param(
    [Parameter(Mandatory = $true)][string]$OutputFile,
    [int]$TimeoutSeconds = 45
)
$ErrorActionPreference = "Stop"
try {
    Add-Type -AssemblyName System.Drawing
    Add-Type -AssemblyName System.Windows.Forms
    Add-Type -TypeDefinition @'
using System;
using System.Runtime.InteropServices;
public static class CodeTraceWindowRect {
    [StructLayout(LayoutKind.Sequential)]
    public struct RECT {
        public int Left, Top, Right, Bottom;
    }
    [DllImport("user32.dll")]
    public static extern bool GetWindowRect(IntPtr window, out RECT rectangle);
}
'@

    $deadline = (Get-Date).AddSeconds($TimeoutSeconds)
    $application = $null
    while ((Get-Date) -lt $deadline) {
        $application = Get-Process -ErrorAction SilentlyContinue | Where-Object {
            $_.MainWindowHandle -ne [IntPtr]::Zero -and
            $_.MainWindowTitle -match 'CodeTrace'
        } | Select-Object -First 1
        if ($application) { break }
        Start-Sleep -Milliseconds 500
    }
    if (-not $application) {
        Write-Output "No se encontro la ventana CodeTrace."
        exit 1
    }

    Start-Sleep -Seconds 2
    $rect = [CodeTraceWindowRect+RECT]::new()
    if (-not [CodeTraceWindowRect]::GetWindowRect(
        $application.MainWindowHandle, [ref]$rect)) {
        throw "No se pudo consultar el rectangulo de la ventana."
    }
    # Only the visible CodeTrace rectangle, not the whole desktop.
    $bounds = [System.Windows.Forms.SystemInformation]::VirtualScreen
    $left = [Math]::Max($rect.Left, $bounds.Left)
    $top = [Math]::Max($rect.Top, $bounds.Top)
    $right = [Math]::Min($rect.Right, $bounds.Right)
    $bottom = [Math]::Min($rect.Bottom, $bounds.Bottom)
    $width = $right - $left
    $height = $bottom - $top
    if ($width -lt 100 -or $height -lt 100) {
        throw "CodeTrace no es visible en el escritorio."
    }
    $bitmap = [System.Drawing.Bitmap]::new($width, $height)
    $graphics = [System.Drawing.Graphics]::FromImage($bitmap)
    try {
        $size = [System.Drawing.Size]::new($width, $height)
        $graphics.CopyFromScreen($left, $top, 0, 0, $size)
        $bitmap.Save($OutputFile, [System.Drawing.Imaging.ImageFormat]::Png)
        Write-Output ("Captura: " + $OutputFile)
    } finally {
        $graphics.Dispose()
        $bitmap.Dispose()
    }
} catch {
    Write-Output ("No se pudo capturar CodeTrace: " + $_.Exception.Message)
    exit 1
}
