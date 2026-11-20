#Requires -Version 5.1
[CmdletBinding()]
param()

$ErrorActionPreference = "Stop"
$root = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$fixture = Join-Path $root "target\qa-preview"
$assets = Join-Path $fixture "assets"
New-Item -ItemType Directory -Force -Path $assets | Out-Null

$logo = Join-Path $root "src\main\resources\icons\codetrace-window.png"
Copy-Item -LiteralPath $logo -Destination (Join-Path $assets "logo con espacio.png") -Force
# Always include a genuine, tiny WebP even when FFmpeg is unavailable.
# Eight-by-eight solid steel-blue image for decoder regression.
$webp = 'UklGRjAAAABXRUJQVlA4ICQAAABQAQCdASoIAAgAAUAmJQBOgCgAAP7vvVcjt+uiY7wbJFNdgAA='
[IO.File]::WriteAllBytes((Join-Path $assets 'sample.webp'),
    [Convert]::FromBase64String($webp))
$svg = @'
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 240 120">
  <rect x="0" y="0" width="240" height="120" fill="#E7EDF1"/>
  <rect x="20" y="24" width="200" height="72" fill="#4A6076"/>
  <circle cx="68" cy="60" r="18" fill="#26834B"/>
  <text x="104" y="68" font-size="18" fill="white">CodeTrace</text>
</svg>
'@
[IO.File]::WriteAllText((Join-Path $assets "seguro.svg"), $svg,
    [Text.UTF8Encoding]::new($false))
$markdown = @'
# CodeTrace — prueba visual Markdown 🚀

Prueba Unicode y alias de GitHub: :rocket: :white_check_mark: :smile:.

## PNG relativo con espacios

![Logo del proyecto](assets/logo%20con%20espacio.png)

## Imagen HTML centrada

<p align="center">
<img src="assets/logo%20con%20espacio.png" alt="Logo centrado" width="100" />
</p>

## SVG seguro

![Diagrama vectorial](assets/seguro.svg)

## WebP generado por FFmpeg si está disponible

![Imagen WebP](assets/sample.webp)

## Tabla Markdown

| Función | Comportamiento |
|---|---|
| Consultar | Solo lectura |
| Reconstruir | Espacio virtual |

## Bloque de código intacto

~~~java
// El alias :rocket: debe conservarse aquí.
class Demo { public static void main(String[] args) {} }
~~~

## Recursos ausentes y externos

![Imagen inexistente](assets/no-existe.png)

![Imagen externa con autorización](https://raw.githubusercontent.com/github/explore/main/topics/java/java.png)

El contenido externo debe permanecer bloqueado hasta habilitarlo manualmente.
'@
[IO.File]::WriteAllText((Join-Path $fixture "README.md"), $markdown,
    [Text.UTF8Encoding]::new($false))
$sourceFolder = Join-Path $fixture "src"
New-Item -ItemType Directory -Force -Path $sourceFolder | Out-Null
$sampleJava = @'
public class Demo {
    public static void main(String[] args) {
        System.out.println("CodeTrace busca sin reemplazar");
        int total = sumar(7, 8);
        System.out.println(total);
    }

    static int sumar(int a, int b) {
        return a + b;
    }
}
'@
[IO.File]::WriteAllText((Join-Path $sourceFolder "Demo.java"), $sampleJava,
    [Text.UTF8Encoding]::new($false))
# Deterministic import graph fixtures for several programming languages.
$demoJava = Join-Path $sourceFolder "demo"
$web = Join-Path $fixture "web"
$css = Join-Path $fixture "styles"
$python = Join-Path $fixture "python"
$native = Join-Path $fixture "native"
$include = Join-Path $fixture "include"
New-Item -ItemType Directory -Force -Path $demoJava,$web,$css,$python,$native,$include | Out-Null
[IO.File]::WriteAllText((Join-Path $demoJava "A.java"), @'
package demo;
import demo.B;
public class A { B dato; }
'@, [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText((Join-Path $demoJava "B.java"), @'
package demo;
public class B { }
'@, [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText((Join-Path $web "main.ts"), @'
import { sumar } from "./util";
console.log(sumar(2, 3));
'@, [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText((Join-Path $web "util.ts"), @'
export const sumar = (a: number, b: number) => a + b;
'@, [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText((Join-Path $python "app.py"), @'
import helper
print(helper.mcd(48, 18))
'@, [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText((Join-Path $python "helper.py"), @'
def mcd(a: int, b: int) -> int:
    return a if b == 0 else mcd(b, a % b)
'@, [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText((Join-Path $native "app.c"), @'
#include "demo.h"
int main(void) { return valor(); }
'@, [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText((Join-Path $include "demo.h"), @'
static inline int valor(void) { return 42; }
'@, [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText((Join-Path $css "theme.css"), @'
/* Comentario verde */
.root { -fx-font-size: 12px; -fx-base: #f5f6f7; }
'@, [Text.UTF8Encoding]::new($false))

[IO.File]::WriteAllText((Join-Path $fixture "config.json"),
    '{"demo":"CodeTrace","version":1,"objetivo":"buscar en proyecto"}',
    [Text.UTF8Encoding]::new($false))
[IO.File]::WriteAllText((Join-Path $fixture "prueba.bat"),
    "@echo off" + [Environment]::NewLine + "echo CodeTrace",
    [Text.Encoding]::ASCII)
[IO.File]::WriteAllText((Join-Path $fixture "prueba.cmd"),
    "@echo off" + [Environment]::NewLine + "echo Vista previa",
    [Text.Encoding]::ASCII)

$ffmpeg = Get-Command ffmpeg.exe -ErrorAction SilentlyContinue
if ($ffmpeg) {
    $samples = @(
        @("-f","lavfi","-i","color=c=steelblue:s=128x128:d=1",
          "-frames:v","1", (Join-Path $assets "sample.webp")),
        @("-f","lavfi","-i","sine=frequency=440:duration=2",
          "-c:a","libmp3lame", (Join-Path $assets "sample.mp3")),
        @("-f","lavfi","-i","sine=frequency=440:duration=2",
          "-c:a","flac", (Join-Path $assets "sample.flac")),
        @("-f","lavfi","-i","sine=frequency=440:duration=2",
          "-c:a","libvorbis", (Join-Path $assets "sample.ogg")),
        @("-f","lavfi","-i","testsrc=size=320x180:rate=15",
          "-t","2","-pix_fmt","yuv420p","-c:v","libx264",
          (Join-Path $assets "sample.mp4")),
        @("-f","lavfi","-i","testsrc=size=320x180:rate=15",
          "-t","2","-pix_fmt","yuv420p","-c:v","libx264",
          (Join-Path $assets "sample.mkv"))
    )
    foreach ($sample in $samples) {
        $target = $sample[-1]
        & $ffmpeg.Source -nostdin -hide_banner -loglevel error -y @sample
        if ($LASTEXITCODE -ne 0) {
            Write-Warning ("No se generó " + $target + ". Comprueba los códecs de FFmpeg.")
        }
    }
} else {
    Write-Warning "FFmpeg no está en PATH; se generaron los ejemplos de imagen y Markdown."
}
Write-Host "Fixtures de CodeTrace: $fixture"
Write-Host "Abre esa carpeta como referencia: colores CSS, grafo de imports, imágenes y multimedia."
