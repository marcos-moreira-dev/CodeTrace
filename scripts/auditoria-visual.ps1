#Requires -Version 5.1
<#
Capturas guiadas para auditoria de UX/UI de CodeTrace.
No sube archivos y no modifica la referencia. Revisa las capturas: pueden
mostrar rutas personales, proyectos, nombres o contenido privado.
#>
[CmdletBinding()]
param([int]$SecondsToFocus = 3, [switch]$NoExplorer)
$ErrorActionPreference = "Stop"
$root = (Resolve-Path -LiteralPath (Join-Path $PSScriptRoot "..")).Path
$reports = Join-Path $root "reports"
$stamp = Get-Date -Format "yyyyMMdd-HHmmss"
$work = Join-Path $reports ("CodeTrace-UX-" + $stamp)
$screens = Join-Path $work "screenshots"
$zip = Join-Path $reports ("CodeTrace-auditoria-UX-" + $stamp + ".zip")
New-Item -ItemType Directory -Force -Path $screens | Out-Null
$capturer = Join-Path $PSScriptRoot "capturar-ventana.ps1"
$powershellExe = Join-Path $env:SystemRoot "System32\WindowsPowerShell\v1.0\powershell.exe"
if (-not (Test-Path -LiteralPath $powershellExe)) { throw "Windows PowerShell 5.1 es necesario." }
$manifest = New-Object "System.Collections.Generic.List[string]"
$manifest.Add("CODETRACE - AUDITORIA VISUAL UX/UI")
$manifest.Add("Fecha: " + (Get-Date -Format "yyyy-MM-dd HH:mm:ss"))
$manifest.Add("Ninguna captura se envia a Internet de forma automatica.")
$manifest.Add("Revisa datos privados antes de compartir el ZIP.")
$manifest.Add("")

$casesText = @'
01-inicio-vacio|Workspace sin pestañas|Cierra todas las pestañas para mostrar la bienvenida
02-proyecto-explorador|Explorador|Abre un proyecto o referencia con carpetas
03-arbol-expandido|Arbol recursivo|Expande varias carpetas y subcarpetas
04-menu-carpeta|Menu contextual de carpeta|Haz clic derecho en una carpeta
05-menu-archivo|Menu contextual de archivo|Haz clic derecho en un archivo
06-menu-pestanas|Pestañas|Abre varias pestañas y muestra sus acciones
07-editor-pendiente|Editor sin escribir|Abre una referencia de codigo nueva
08-editor-aciertos|Editor en progreso|Muestra codigo transcrito correctamente
09-editor-errores|Errores y correcciones|Deja visibles errores en un archivo de prueba
10-finalizacion|Archivo terminado|Muestra el dialogo de felicitacion despues de completar
11-modalidad-directa|Modo directo|Muestra las opciones de reconstruccion directa
12-modalidad-manual|Modo manual|Muestra las opciones de reconstruccion manual
13-filtros-progreso|Reglas de practica|Abre los filtros de progreso y exclusiones
14-markdown-fuente|Markdown fuente|Muestra la vista de texto Markdown
15-markdown-renderizado|Markdown renderizado|Muestra su previsualizacion con imagenes
16-imagen|Imagen|Abre una imagen y muestra el zoom
17-svg|SVG|Abre un archivo SVG
18-audio|Audio|Abre los controles de audio
19-video|Video|Abre un video con sus controles
20-video-salto|Video seek|Salta a otro minuto del video
21-grafo-general|Grafo|Abre el grafo de dependencias
22-grafo-zoom|Grafo navegacion|Muestra zoom paneo o un nodo seleccionado
23-busqueda|Busqueda|Abre la ventana de busqueda con resultados
24-consola-vacia|Consola|Muestra la consola sin ejecuciones
25-consola-uso|Comando seguro|Muestra una orden inocua de prueba
26-preferencias-generales|Preferencias generales|Muestra las opciones generales
27-preferencias-practica|Preferencias de escritura|Muestra tipografia y mecanografia
28-preferencias-sonido|Preferencias de sonido|Muestra las opciones de sonido de finalizacion
29-preferencias-comandos|Preferencias de comandos|Muestra la configuracion de comandos
30-biblioteca|Biblioteca de practica|Abre la biblioteca incorporada
31-guardar|Guardar sesion|Abre el cuadro Guardar sin confirmar
32-restaurar|Abrir sesion|Abre el cuadro Abrir sesion sin confirmar
33-acerca-de|Acerca de|Abre Acerca de CodeTrace
34-artefacto-no-practica|Artefacto de consulta|Doble clic en un archivo que no requiere mecanografia
35-concentracion|Modo concentracion|Activa el modo concentracion
36-ventana-pequena|Ventana estrecha|Reduce el tamaño de la ventana
37-otros|Otros casos|Captura cualquier otro estado o problema
'@
$cases = @($casesText.Trim() -split "\r?\n")
Write-Host "CODETRACE | Auditoria visual guiada" -ForegroundColor Cyan
Write-Host "Abre primero CodeTrace con un proyecto de prueba."
Write-Host "Cada caso: prepara la pantalla, confirma INTRO, y se captura."
Write-Host "S omite un caso; Q termina y genera el ZIP."
Write-Host "Las capturas pueden contener datos personales: revisalas antes de enviarlas."
$number = 0
try {
    foreach ($item in $cases) {
        $number++
        $values = $item.Split('|')
        $slug = $values[0]; $title = $values[1]; $hint = $values[2]
        Write-Host ""
        Write-Host ("[{0}/{1}] {2}" -f $number, $cases.Count, $title) -ForegroundColor Yellow
        Write-Host $hint
        $answer = (Read-Host "INTRO capturar / S omitir / Q terminar").Trim()
        if ($answer -match '^[qQ]$') {
            $manifest.Add("$slug | FIN VOLUNTARIO")
            break
        }
        if ($answer -match '^[sS]$') {
            $manifest.Add("$slug | OMITIDO")
            continue
        }
        try {
            $app = Get-Process -ErrorAction SilentlyContinue |
                Where-Object { $_.MainWindowHandle -ne [IntPtr]::Zero -and $_.MainWindowTitle -match 'CodeTrace' } |
                Select-Object -First 1
            if ($app) {
                $shell = New-Object -ComObject WScript.Shell
                [void]$shell.AppActivate($app.Id)
            }
        } catch { }
        Write-Host "Enfoca la ventana CodeTrace para capturarla..."
        Start-Sleep -Seconds ([Math]::Max(1, $SecondsToFocus))
        $target = Join-Path $screens ($slug + ".png")
        $outputText = (& $powershellExe -NoProfile -ExecutionPolicy Bypass -File $capturer -OutputFile $target -TimeoutSeconds 10 2>&1 | Out-String)
        if ((Test-Path -LiteralPath $target) -and (Get-Item -LiteralPath $target).Length -gt 0) {
            Write-Host "Captura correcta." -ForegroundColor Green
            $manifest.Add("$slug | CAPTURADO | $title | $hint")
        } else {
            Write-Warning ("No se capturo " + $slug)
            $manifest.Add("$slug | ERROR | $outputText")
        }
    }
} finally {
    $manifest.Add("")
    $manifest.Add("El ZIP contiene imagenes e indice. No se envio nada automaticamente.")
    [System.IO.File]::WriteAllLines((Join-Path $work "INDICE-Y-PRIVACIDAD.txt"), [string[]]$manifest, [System.Text.UTF8Encoding]::new($true))
    Compress-Archive -Path (Join-Path $work "*") -DestinationPath $zip -CompressionLevel Optimal -Force
    Write-Host ("ZIP: " + $zip) -ForegroundColor Cyan
    Write-Host "Revisa y elimina PNG con datos privados antes de compartir."
    if (-not $NoExplorer) {
        Start-Process -FilePath explorer.exe -ArgumentList ('/select,"' + $zip + '"')
    }
}
