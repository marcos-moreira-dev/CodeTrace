#Requires -Version 5.1
[CmdletBinding()]
param([switch]$Installer, [switch]$IncludeFfmpeg)

$ErrorActionPreference = "Stop"
$root = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
Set-Location -LiteralPath $root

# Toolchains and jpackage must agree on a full Temurin JDK 21 installation.
$jdk = $env:JAVA_HOME
if ([string]::IsNullOrWhiteSpace($jdk)) {
    $compiler = Get-Command javac.exe -ErrorAction SilentlyContinue
    if ($compiler) { $jdk = Split-Path -Parent (Split-Path -Parent $compiler.Source) }
}
if (-not $jdk -or -not (Test-Path (Join-Path $jdk "bin\jpackage.exe"))) {
    throw "Se necesita Eclipse Temurin JDK 21 completo y JAVA_HOME configurado."
}
$release = Join-Path $jdk "release"
if (-not (Test-Path $release) -or
        ([IO.File]::ReadAllText($release) -notmatch '(?m)^JAVA_VERSION="21(\.|")') -or
        ([IO.File]::ReadAllText($release) -notmatch '(?m)^IMPLEMENTOR="Eclipse Adoptium"')) {
    throw "JAVA_HOME debe apuntar a Eclipse Temurin JDK 21."
}
$mvn = (Get-Command mvn.cmd -ErrorAction Stop).Source
$target = Join-Path $root "target"
$input = Join-Path $target "package-input"
$dist = Join-Path $root "dist"
New-Item -ItemType Directory -Force -Path $target | Out-Null
$toolchains = Join-Path $env:TEMP ("codetrace-toolchains-" + [guid]::NewGuid().ToString("N") + ".xml")
$escapedJdk = [Security.SecurityElement]::Escape($jdk)
$xml = @"
<?xml version="1.0" encoding="UTF-8"?>
<toolchains><toolchain><type>jdk</type><provides>
<version>21</version><vendor>temurin</vendor>
</provides><configuration><jdkHome>$escapedJdk</jdkHome></configuration>
</toolchain></toolchains>
"@
[IO.File]::WriteAllText($toolchains, $xml,
    [Text.UTF8Encoding]::new($false))
New-Item -ItemType Directory -Force -Path $input,$dist | Out-Null

# Package pinned IBM Plex fonts when the network or a local cache permits it.
# Optional fonts must never prevent an offline build.
try {
    & (Join-Path $PSScriptRoot "preparar-fuentes.ps1")
} catch {
    Write-Warning "No se pudieron preparar las fuentes incluidas: $_"
}
# Offline vectors are optional for development; the release image fetches
# Twemoji once and bundles resources. CodeTrace never downloads on preview.
try {
    & (Join-Path $PSScriptRoot "preparar-emojis.ps1")
} catch {
    Write-Warning "No se pudieron preparar todos los emojis SVG: $_"
}
Write-Host "Verificando CodeTrace y preparando dependencias..."
$mavenArgs = @("-B", "-ntp", "-t", $toolchains, "clean", "verify",
    "dependency:copy-dependencies", "-DincludeScope=runtime",
    "-DoutputDirectory=$input")
& $mvn @mavenArgs
if ($LASTEXITCODE -ne 0) { throw "Falló Maven verify o la preparación de dependencias." }

# Derive the artifact name from pom.xml. This works for both SNAPSHOT builds
# and the eventual 0.3.0 release without editing the packaging script.
[xml]$pomModel = Get-Content -LiteralPath (Join-Path $root "pom.xml") -Raw
$artifact = $pomModel.SelectSingleNode("/*[local-name()='project']/*[local-name()='artifactId']").InnerText.Trim()
$version = $pomModel.SelectSingleNode("/*[local-name()='project']/*[local-name()='version']").InnerText.Trim()
if ([string]::IsNullOrWhiteSpace($artifact) -or [string]::IsNullOrWhiteSpace($version)) {
    throw "El pom.xml no tiene artifactId o version válidos."
}
# Windows installers need a numeric version (POM development versions may end in -SNAPSHOT).
$appVersion = ($version -replace '-.*$', '')
if ($appVersion -notmatch '^\d+(\.\d+){0,3}$') {
    throw "Versión inválida para el instalador Windows: $version"
}
$jarName = "$artifact-$version.jar"
$jar = Join-Path $target $jarName
if (-not (Test-Path -LiteralPath $jar)) { throw "No existe el JAR compilado: $jarName" }
Copy-Item -LiteralPath $jar -Destination $input -Force
# Include licensing and third-party attribution in both portable and installer.
# jpackage copies all files from --input into the image's app directory.
$legalDir = Join-Path $input "licenses"
New-Item -ItemType Directory -Force -Path $legalDir | Out-Null
$legalFiles = @(
    @((Join-Path $root "LICENSE"), "CodeTrace-MIT.txt"),
    @((Join-Path $root "LICENSING.md"), "LICENSING.md"),
    @((Join-Path $root "THIRD-PARTY-NOTICES.md"), "THIRD-PARTY-NOTICES.md"),
    @((Join-Path $root "src\main\resources\fonts\OFL.txt"), "IBM-Plex-OFL.txt"),
    @((Join-Path $root "src\main\resources\emojis\twemoji\LICENSE-GRAPHICS"), "Twemoji-CC-BY-4.0.txt")
)
foreach ($item in $legalFiles) {
    if (-not (Test-Path -LiteralPath $item[0] -PathType Leaf)) {
        throw "Falta el aviso legal para empaquetar: $($item[0])"
    }
    Copy-Item -LiteralPath $item[0] -Destination (Join-Path $legalDir $item[1]) -Force
}
# Windows shell and taskbar must display the official CodeTrace mark,
# not jpackage's fallback Java icon. Reuse the same ICO converter that
# generates the distinct .ctrace document icon for the installer.
$appIcon = Join-Path $target "codetrace-app.ico"
& (Join-Path $PSScriptRoot "crear-icono-sesion.ps1") `
    -Source (Join-Path $root "src\main\resources\icons\codetrace-window.png") `
    -Destination $appIcon
if (-not (Test-Path -LiteralPath $appIcon -PathType Leaf)) {
    throw "No se generó el icono principal de CodeTrace."
}
$jpackage = Join-Path $jdk "bin\jpackage.exe"
$portable = Join-Path $dist "CodeTrace"
if (Test-Path $portable) { Remove-Item -LiteralPath $portable -Recurse -Force }

Write-Host "Creando aplicación portable de Windows (JRE incluido)..."
$packageArgs = @("--type", "app-image", "--name", "CodeTrace",
    "--input", $input, "--main-jar", $jarName,
    "--main-class", "com.mmdev.codetrace.app.Launcher",
    "--dest", $dist, "--java-options", "-Dfile.encoding=UTF-8",
    "--app-version", $appVersion, "--vendor", "Marcos Moreira DEV",
    "--icon", $appIcon)
& $jpackage @packageArgs
if ($LASTEXITCODE -ne 0) { throw "jpackage no pudo generar la aplicación." }

# Optional redistribution: supply a legally redistributable FFmpeg build,
# its corresponding license and required notices/sources.
if ($IncludeFfmpeg) {
    $thirdParty = Join-Path $root "third_party\ffmpeg"
    $binary = Join-Path $thirdParty "bin\ffmpeg.exe"
    $notice = Join-Path $thirdParty "LICENSE.txt"
    $probe = Join-Path $thirdParty "bin\ffprobe.exe"
    if (-not (Test-Path $binary) -or -not (Test-Path $notice)) {
        throw "Para incluir FFmpeg se requieren third_party\ffmpeg\bin\ffmpeg.exe y LICENSE.txt. Revisa también las obligaciones de su licencia."
    }
    $destination = Join-Path $portable "tools\ffmpeg"
    New-Item -ItemType Directory -Force -Path $destination | Out-Null
    Copy-Item -LiteralPath $binary -Destination $destination
    if (Test-Path -LiteralPath $probe) { Copy-Item -LiteralPath $probe -Destination $destination }
    Copy-Item -LiteralPath $notice -Destination $destination
    Write-Host "FFmpeg incorporado. La redistribución exige revisar licencias y fuentes."
}
$zip = Join-Path $dist "CodeTrace-Windows-portable.zip"
if (Test-Path $zip) { Remove-Item $zip -Force }
Compress-Archive -Path $portable -DestinationPath $zip -CompressionLevel Optimal
# Publish a checksum beside the ZIP so users can verify downloaded artifacts
# without installing Java, Maven or the application itself.
$hash = (Get-FileHash -LiteralPath $zip -Algorithm SHA256).Hash.ToLowerInvariant()
$checksumFile = "$zip.sha256"
[IO.File]::WriteAllText($checksumFile,
        "$hash  CodeTrace-Windows-portable.zip" + [Environment]::NewLine,
        [Text.Encoding]::ASCII)
Write-Host "Portable listo: $zip"
Write-Host "SHA-256: $checksumFile"
if ($Installer) {
    Write-Host "Creando instalador EXE (requiere WiX Toolset compatible)..."
    # The currently supported session format is .ctrace (never .ctsession).
    # jpackage registers its distinct icon and passes the selected file path.
    $iconPath = Join-Path $target "codetrace-session.ico"
    & (Join-Path $PSScriptRoot "crear-icono-sesion.ps1") -Destination $iconPath
    if (-not (Test-Path -LiteralPath $iconPath -PathType Leaf)) {
        throw "No se generó el icono de sesiones."
    }
    $association = Join-Path $target "session-association.properties"
    $iconUri = $iconPath.Replace('\', '/')
    $properties = @(
        "extension=ctrace",
        "mime-type=application/x-codetrace-session",
        "description=Sesion de CodeTrace",
        "icon=$iconUri"
    ) -join [Environment]::NewLine
    [IO.File]::WriteAllText($association, $properties,
            [Text.UTF8Encoding]::new($false))
    $installerArgs = @("--type", "exe", "--app-image", $portable,
        "--name", "CodeTrace", "--app-version", $appVersion, "--dest", $dist,
        "--win-menu", "--win-shortcut", "--file-associations", $association)
    & $jpackage @installerArgs
    if ($LASTEXITCODE -ne 0) { throw "No se creó el instalador EXE; verifica WiX." }
}
Remove-Item -LiteralPath $toolchains -Force -ErrorAction SilentlyContinue
Write-Host "Prueba CodeTrace.exe en un equipo Windows limpio antes de distribuir."
