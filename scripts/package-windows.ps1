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

Write-Host "Verificando CodeTrace y preparando dependencias..."
$mavenArgs = @("-B", "-ntp", "-t", $toolchains, "clean", "verify",
    "dependency:copy-dependencies", "-DincludeScope=runtime",
    "-DoutputDirectory=$input")
& $mvn @mavenArgs
if ($LASTEXITCODE -ne 0) { throw "Falló Maven verify o la preparación de dependencias." }

$jar = Join-Path $target "codetrace-0.3.0-SNAPSHOT.jar"
if (-not (Test-Path -LiteralPath $jar)) { throw "No existe el JAR compilado." }
Copy-Item -LiteralPath $jar -Destination $input -Force
$jpackage = Join-Path $jdk "bin\jpackage.exe"
$portable = Join-Path $dist "CodeTrace"
if (Test-Path $portable) { Remove-Item -LiteralPath $portable -Recurse -Force }

Write-Host "Creando aplicación portable de Windows (JRE incluido)..."
$packageArgs = @("--type", "app-image", "--name", "CodeTrace",
    "--input", $input, "--main-jar", "codetrace-0.3.0-SNAPSHOT.jar",
    "--main-class", "com.mmdev.codetrace.app.Launcher",
    "--dest", $dist, "--java-options", "-Dfile.encoding=UTF-8")
& $jpackage @packageArgs
if ($LASTEXITCODE -ne 0) { throw "jpackage no pudo generar la aplicación." }

# Optional redistribution: supply a legally redistributable FFmpeg build,
# its corresponding license and required notices/sources.
if ($IncludeFfmpeg) {
    $thirdParty = Join-Path $root "third_party\ffmpeg"
    $binary = Join-Path $thirdParty "bin\ffmpeg.exe"
    $notice = Join-Path $thirdParty "LICENSE.txt"
    if (-not (Test-Path $binary) -or -not (Test-Path $notice)) {
        throw "Para incluir FFmpeg se requieren third_party\ffmpeg\bin\ffmpeg.exe y LICENSE.txt. Revisa también las obligaciones de su licencia."
    }
    $destination = Join-Path $portable "tools\ffmpeg"
    New-Item -ItemType Directory -Force -Path $destination | Out-Null
    Copy-Item -LiteralPath $binary -Destination $destination
    Copy-Item -LiteralPath $notice -Destination $destination
    Write-Host "FFmpeg incorporado. La redistribución exige revisar licencias y fuentes."
}
$zip = Join-Path $dist "CodeTrace-Windows-portable.zip"
if (Test-Path $zip) { Remove-Item $zip -Force }
Compress-Archive -Path $portable -DestinationPath $zip -CompressionLevel Optimal
Write-Host "Portable listo: $zip"
if ($Installer) {
    Write-Host "Creando instalador EXE (requiere WiX Toolset compatible)..."
    & $jpackage --type exe --app-image $portable --name CodeTrace --dest $dist
    if ($LASTEXITCODE -ne 0) { throw "No se creó el instalador EXE; verifica WiX." }
}
Remove-Item -LiteralPath $toolchains -Force -ErrorAction SilentlyContinue
Write-Host "Prueba CodeTrace.exe en un equipo Windows limpio antes de distribuir."
