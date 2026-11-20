#Requires -Version 5.1
<#
Captura exclusivamente la ventana en primer plano de un PROCESO CodeTrace
verificado. No compara el titulo con "CodeTrace": la terminal de QA tambien
contiene ese nombre y anteriormente podia capturarse por equivocacion.
Exit 2: ninguna ventana valida; no se escribe PNG.
#>
[CmdletBinding()]
param(
    [Parameter(Mandatory = $true)][string]$OutputFile,
    [int]$TimeoutSeconds = 4
)
$ErrorActionPreference = "Stop"
try {
    Add-Type -AssemblyName System.Drawing
    Add-Type -AssemblyName System.Windows.Forms
    if (-not ("CodeTraceWindowCapture" -as [type])) {
        Add-Type -TypeDefinition @'
using System;
using System.Text;
using System.Runtime.InteropServices;
public static class CodeTraceWindowCapture {
    [StructLayout(LayoutKind.Sequential)]
    public struct RECT { public int Left, Top, Right, Bottom; }
    [DllImport("user32.dll")] public static extern bool GetWindowRect(IntPtr hwnd, out RECT rect);
    [DllImport("user32.dll")] public static extern IntPtr GetForegroundWindow();
    [DllImport("user32.dll")] public static extern uint GetWindowThreadProcessId(IntPtr hwnd, out uint pid);
    [DllImport("user32.dll", CharSet=CharSet.Unicode)]
    public static extern int GetWindowText(IntPtr hwnd, StringBuilder title, int capacity);
    [DllImport("user32.dll")] public static extern bool IsWindowVisible(IntPtr hwnd);
}
'@
    }

    # Never activate a terminal or a title-matched unrelated window.
    # Only the foreground window of CodeTrace.exe or CodeTrace's JVM is valid.
    $deadline = (Get-Date).AddSeconds([Math]::Max(0, $TimeoutSeconds))
    $captureHandle = [IntPtr]::Zero
    $windowTitle = ""
    do {
        $foreground = [CodeTraceWindowCapture]::GetForegroundWindow()
        if ($foreground -ne [IntPtr]::Zero -and
                [CodeTraceWindowCapture]::IsWindowVisible($foreground)) {
            [uint32]$ownerPid = 0
            [void][CodeTraceWindowCapture]::GetWindowThreadProcessId(
                $foreground, [ref]$ownerPid)
            if ($ownerPid -gt 0) {
                # Win32_Process provides the actual executable and command line.
                $proc = Get-CimInstance Win32_Process -Filter ("ProcessId=" + $ownerPid) -ErrorAction SilentlyContinue
                if ($null -ne $proc) {
                    $name = [string]$proc.Name
                    $cmd = [string]$proc.CommandLine
                    $nativeLauncher = $name -ieq "CodeTrace.exe"
                    $jvm = ($name -ieq "java.exe" -or $name -ieq "javaw.exe") -and
                        ($cmd -match '(?i)com[.]mmdev[.]codetrace' -or
                         $cmd -match '(?i)codetrace[^\\ ]*[.]jar')
                    if ($nativeLauncher -or $jvm) {
                        $captureHandle = $foreground
                        $title = New-Object System.Text.StringBuilder 512
                        [void][CodeTraceWindowCapture]::GetWindowText(
                            $foreground, $title, $title.Capacity)
                        $windowTitle = $title.ToString()
                        break
                    }
                }
            }
        }
        if ((Get-Date) -ge $deadline) { break }
        Start-Sleep -Milliseconds 250
    } while ($true)

    if ($captureHandle -eq [IntPtr]::Zero) {
        Write-Output "SIN_CAPTURA: enfoca una ventana de la aplicación CodeTrace."
        exit 2
    }
    $rect = [CodeTraceWindowCapture+RECT]::new()
    if (-not [CodeTraceWindowCapture]::GetWindowRect(
            $captureHandle, [ref]$rect)) {
        throw "No se pudo determinar el rectangulo de CodeTrace."
    }
    $screen = [System.Windows.Forms.SystemInformation]::VirtualScreen
    $left = [Math]::Max($rect.Left, $screen.Left)
    $top = [Math]::Max($rect.Top, $screen.Top)
    $right = [Math]::Min($rect.Right, $screen.Right)
    $bottom = [Math]::Min($rect.Bottom, $screen.Bottom)
    $width = $right - $left
    $height = $bottom - $top
    if ($width -lt 100 -or $height -lt 100) {
        throw "La ventana CodeTrace esta fuera de la pantalla o minimizada."
    }
    $bitmap = New-Object System.Drawing.Bitmap($width, $height)
    $graphics = [System.Drawing.Graphics]::FromImage($bitmap)
    try {
        $graphics.CopyFromScreen($left, $top, 0, 0,
            [System.Drawing.Size]::new($width, $height))
        $directory = Split-Path -Parent $OutputFile
        if ($directory -and -not (Test-Path -LiteralPath $directory)) {
            New-Item -ItemType Directory -Path $directory -Force | Out-Null
        }
        $bitmap.Save($OutputFile, [System.Drawing.Imaging.ImageFormat]::Png)
        Write-Output ("CAPTURA: " + $OutputFile)
        Write-Output ("VENTANA: " + $windowTitle)
    } finally {
        $graphics.Dispose()
        $bitmap.Dispose()
    }
} catch {
    Write-Output ("SIN_CAPTURA: " + $_.Exception.Message)
    exit 2
}
