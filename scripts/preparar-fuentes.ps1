#Requires -Version 5.1
<#
Download four unmodified IBM Plex Open Font License 1.1 fonts into the
application's classpath before packaging. Files are NOT installed in Windows.
A pinned upstream commit plus Git blob SHA-1 verification prevents accidental
changes in downloaded files. An offline build may use the existing files;
if unavailable CodeTrace falls back to the OS defaults.
#>
[CmdletBinding()]
param([switch]$Required)
$ErrorActionPreference = "Stop"
$root = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot "..")).Path
$destination = Join-Path $root "src\main\resources\fonts"
New-Item -ItemType Directory -Force -Path $destination | Out-Null
$upstream = "2e90fe366fdc27c12682db13e5aa20ded3d90908"
$baseUrl = "https://raw.githubusercontent.com/IBM/plex/$upstream"
$entries = @(
    @("IBMPlexSans-Regular.ttf", "packages/plex-sans/fonts/complete/ttf/IBMPlexSans-Regular.ttf", "6ef8dd899da4c15af7bc42f58d8109e07d843a94"),
    @("IBMPlexSans-Bold.ttf", "packages/plex-sans/fonts/complete/ttf/IBMPlexSans-Bold.ttf", "bd0f922a7b615451015711c90a6c75da31fdb526"),
    @("IBMPlexMono-Regular.ttf", "packages/plex-mono/fonts/complete/ttf/IBMPlexMono-Regular.ttf", "4254c37f20ba57e829a3315fdc91cda4e63589f5"),
    @("IBMPlexMono-Bold.ttf", "packages/plex-mono/fonts/complete/ttf/IBMPlexMono-Bold.ttf", "9a168d21d5f8a951fa1940cf4703926fcccc5ee0")
)
function GitBlobSha([string]$file) {
    [byte[]]$data = [System.IO.File]::ReadAllBytes($file)
    [byte[]]$prefix = [System.Text.Encoding]::ASCII.GetBytes(
        "blob $($data.Length)" + [char]0)
    [byte[]]$full = New-Object byte[] ($prefix.Length + $data.Length)
    [System.Buffer]::BlockCopy($prefix, 0, $full, 0, $prefix.Length)
    [System.Buffer]::BlockCopy($data, 0, $full, $prefix.Length, $data.Length)
    $sha1 = [System.Security.Cryptography.SHA1]::Create()
    try {
        return [System.BitConverter]::ToString($sha1.ComputeHash($full)).
            Replace("-", "").ToLowerInvariant()
    } finally { $sha1.Dispose() }
}
$problems = New-Object "System.Collections.Generic.List[string]"
# Force TLS 1.2 on stock Windows PowerShell 5.1, when available.
[Net.ServicePointManager]::SecurityProtocol =
    [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12
foreach ($entry in $entries) {
    $name = [string]$entry[0]
    $file = Join-Path $destination $name
    $expected = [string]$entry[2]
    if ((Test-Path -LiteralPath $file) -and (GitBlobSha $file) -eq $expected) {
        Write-Host "Fuente lista: $name"
        continue
    }
    $temporary = "$file.download"
    try {
        $uri = "$baseUrl/$($entry[1])"
        Invoke-WebRequest -Uri $uri -UseBasicParsing -OutFile $temporary -ErrorAction Stop
        $actual = GitBlobSha $temporary
        if ($actual -ne $expected) {
            throw "La firma de $name no coincide con el original IBM ($actual)."
        }
        Move-Item -LiteralPath $temporary -Destination $file -Force
        Write-Host "Fuente verificada: $name" -ForegroundColor Green
    } catch {
        $problems.Add("$name : $($_.Exception.Message)")
        Write-Warning ("No se pudo preparar " + $name + ". CodeTrace usará fuentes del sistema.")
    } finally {
        Remove-Item -LiteralPath $temporary -Force -ErrorAction SilentlyContinue
    }
}
if ($problems.Count -gt 0 -and $Required) {
    throw "Faltan fuentes de IBM Plex: $($problems -join '; ')"
}
if ($problems.Count -eq 0) {
    Write-Host "IBM Plex lista para incluir en CodeTrace. Licencia: fonts/OFL.txt." -ForegroundColor Cyan
}
