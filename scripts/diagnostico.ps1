#Requires -Version 5.1
[CmdletBinding()]
param([switch]$NoApp, [switch]$NoExplorer, [switch]$NoScreenshot)

$ErrorActionPreference = "Stop"
$root = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot "..")).Path
Set-Location -LiteralPath $root
$reportRoot = Join-Path $root "reports"
$stamp = Get-Date -Format "yyyyMMdd-HHmmss"
$work = Join-Path $reportRoot ("CodeTrace-" + $stamp)
$logs = Join-Path $work "logs"
$screens = Join-Path $work "screenshots"
$zip = Join-Path $reportRoot ("CodeTrace-feedback-" + $stamp + ".zip")
New-Item -ItemType Directory -Force -Path $logs,$screens | Out-Null
$summary = New-Object System.Collections.Generic.List[string]
$summary.Add("CodeTrace - Diagnostico de Windows")
$summary.Add("Fecha: " + (Get-Date -Format "yyyy-MM-dd HH:mm:ss"))
$summary.Add("Revisa los logs, rutas y capturas antes de compartir este ZIP.")
$code = 0
$capture = $null

function Note([string]$message) {
    Write-Host $message
    $script:summary.Add($message)
}
function Temurin21([string]$home) {
    if ([string]::IsNullOrWhiteSpace($home)) { return $false }
    $home = $home.Trim('"')
    if (-not (Test-Path -LiteralPath (Join-Path $home "bin\javac.exe"))) { return $false }
    $release = Join-Path $home "release"
    if (-not (Test-Path -LiteralPath $release)) { return $false }
    $data = [System.IO.File]::ReadAllText($release)
    return ($data -match '(?m)^JAVA_VERSION="21(\.|")' -and
            $data -match '(?m)^IMPLEMENTOR="Eclipse Adoptium"')
}
function Find-Jdk {
    $choices = New-Object System.Collections.Generic.List[string]
    foreach ($name in @("JAVA_HOME","JAVA21_HOME","JDK21_HOME")) {
        $value = [Environment]::GetEnvironmentVariable($name)
        if ($value) { $choices.Add($value) }
    }
    $cmd = Get-Command javac.exe -ErrorAction SilentlyContinue
    if ($cmd) { $choices.Add((Split-Path -Parent (Split-Path -Parent $cmd.Source))) }
    foreach ($folder in @(
        (Join-Path $env:ProgramFiles "Eclipse Adoptium"),
        (Join-Path $env:USERPROFILE ".jdks"),
        (Join-Path $env:LOCALAPPDATA "Programs\Eclipse Adoptium"),
        (Join-Path $env:ProgramFiles "Java")
    )) {
        if (Test-Path -LiteralPath $folder) {
            foreach ($item in @(Get-ChildItem -LiteralPath $folder -Directory -ErrorAction SilentlyContinue)) {
                if ($item.Name -match '21') { $choices.Add($item.FullName) }
            }
        }
    }
    foreach ($candidate in ($choices | Select-Object -Unique)) {
        if (Temurin21 $candidate) { return $candidate.Trim('"') }
    }
    return $null
}
function Maven-Log([string]$mvn, [string[]]$params, [string]$file) {
    $before = $ErrorActionPreference
    try {
        $ErrorActionPreference = "Continue"
        & $mvn @params 2>&1 | Out-File -FilePath $file -Encoding UTF8
        return [int]$LASTEXITCODE
    } finally {
        $ErrorActionPreference = $before
    }
}

try {
    Note "Buscando Eclipse Temurin JDK 21..."
    $jdk = Find-Jdk
    if (-not $jdk) {
        Note "ERROR: no se encontro Eclipse Temurin JDK 21; configura JAVA_HOME."
        $code = 2
    } else {
        $env:JAVA_HOME = $jdk
        $env:Path = (Join-Path $jdk "bin") + ";" + $env:Path
        Note ("JDK elegido: " + $jdk)
        $maven = Get-Command mvn.cmd -ErrorAction SilentlyContinue
        if (-not $maven) {
            Note "ERROR: Maven (mvn.cmd) no esta en PATH."
            $code = 2
        } else {
            $mvn = $maven.Source
            $tc = Join-Path $work "toolchains.xml"
            $jdkXml = [System.Security.SecurityElement]::Escape($jdk)
            $xml = @"
<?xml version="1.0" encoding="UTF-8"?>
<toolchains>
  <toolchain><type>jdk</type>
    <provides><version>21</version><vendor>temurin</vendor></provides>
    <configuration><jdkHome>$jdkXml</jdkHome></configuration>
  </toolchain>
</toolchains>
"@
            [System.IO.File]::WriteAllText($tc,$xml,[System.Text.UTF8Encoding]::new($false))
            $oldPreference = $ErrorActionPreference
            $ErrorActionPreference = "Continue"
            try {
                $javaVersion = & (Join-Path $jdk "bin\java.exe") -version 2>&1 | Out-String
                $mavenVersion = & $mvn -version 2>&1 | Out-String
            } finally {
                $ErrorActionPreference = $oldPreference
            }
            [System.IO.File]::WriteAllText((Join-Path $logs "versiones.txt"),
                "JAVA:" + [Environment]::NewLine + $javaVersion +
                [Environment]::NewLine + "MAVEN:" + [Environment]::NewLine + $mavenVersion)
            Note "Ejecutando pruebas y compilacion con Maven Toolchains..."
            $result = Maven-Log $mvn @("-B","-ntp","-t",$tc,"clean","verify") (Join-Path $logs "maven-verify.log")
            if ($result -ne 0) {
                Note ("ERROR: Maven verify termino con codigo " + $result)
                $code = 1
            } else {
                Note "OK: Maven verify."
            }
            $junit = Join-Path $root "target\surefire-reports"
            if (Test-Path -LiteralPath $junit) {
                Copy-Item -LiteralPath $junit -Destination (Join-Path $work "junit") -Recurse -Force
            }
            if ($result -eq 0 -and -not $NoApp) {
                if (-not $NoScreenshot) {
                    Note "Intentando captura solo de la ventana CodeTrace..."
                    $capturer = Join-Path $PSScriptRoot "capturar-ventana.ps1"
                    $picture = Join-Path $screens "codetrace.png"
                    $args = '-NoProfile -ExecutionPolicy Bypass -File "' + $capturer +
                            '" -OutputFile "' + $picture + '" -TimeoutSeconds 45'
                    $capture = Start-Process -FilePath (Join-Path $PSHOME "powershell.exe") -ArgumentList $args -WindowStyle Hidden -RedirectStandardOutput (Join-Path $logs "captura.log") -RedirectStandardError (Join-Path $logs "captura-errores.log") -PassThru
                }
                Note "La aplicacion se abre ahora. Cierra CodeTrace para completar el informe."
                $appCode = Maven-Log $mvn @("-B","-ntp","-t",$tc,"javafx:run") (Join-Path $logs "javafx-run.log")
                if ($appCode -ne 0) {
                    Note ("ERROR: javafx:run termino con codigo " + $appCode)
                    $code = 1
                } else {
                    Note "OK: JavaFX termino sin error."
                }
                if ($capture) {
                    if (-not $capture.WaitForExit(5000)) {
                        Stop-Process -Id $capture.Id -Force -ErrorAction SilentlyContinue
                    }
                    if (Test-Path -LiteralPath $picture) {
                        Note "Captura incluida."
                    } else {
                        Note "Captura no disponible; revisar captura.log."
                    }
                }
            }
            if ($NoApp) { Note "Ejecucion grafica omitida por -NoApp." }
        }
    }
} catch {
    $code = 1
    Note ("ERROR inesperado: " + $_.Exception.Message)
    $_ | Out-String | Out-File -FilePath (Join-Path $logs "excepcion.log") -Encoding UTF8
} finally {
    if ($capture -and -not $capture.HasExited) {
        Stop-Process -Id $capture.Id -Force -ErrorAction SilentlyContinue
    }
    Note ("Resultado general: " + $(if ($code -eq 0) { "OK" } else { "REVISAR" }))
    [System.IO.File]::WriteAllLines(
        (Join-Path $work "resultado.txt"),[string[]]$summary,[System.Text.UTF8Encoding]::new($true))
    try {
        Compress-Archive -Path (Join-Path $work "*") -DestinationPath $zip -Force
        Write-Host ("ZIP: " + $zip)
        if (-not $NoExplorer) {
            Start-Process -FilePath explorer.exe -ArgumentList ('/select,"' + $zip + '"')
        }
    } catch {
        Write-Warning ("No se pudo comprimir el informe: " + $_.Exception.Message)
        $code = 1
    }
}
exit $code
