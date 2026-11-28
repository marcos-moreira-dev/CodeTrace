#requires -Version 5.1
# UTF-8 BOM required for the legacy Windows PowerShell 5.1 parser.
<#
.SYNOPSIS
  Audita la biblioteca offline de CodeTrace v27 (1892 referencias / 14 lenguajes).
.DESCRIPTION
  Revisa el catálogo, archivos existentes, carpetas de ejercicio, versiones
  y comandos de compilación. Por defecto NO compila ni modifica referencias.
  -EjecutarClientes utiliza un directorio temporal y JDK 21, que elimina
  después de ejecutar clientes de grafos y patrones.
.EXAMPLE
  powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\auditar-biblioteca.ps1
.EXAMPLE
  powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\auditar-biblioteca.ps1 -EjecutarClientes
#>
[CmdletBinding()]
param([switch] $EjecutarClientes)
$ErrorActionPreference = 'Stop'
$raiz = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$biblioteca = Join-Path $raiz 'src\main\resources\practice'
$catalogo = Join-Path $biblioteca 'catalog.tsv'
if (-not (Test-Path -LiteralPath $catalogo -PathType Leaf)) {
    throw "No se encontró el catálogo: $catalogo"
}

$referencias = New-Object 'System.Collections.Generic.List[object]'
$rutasVistas = [System.Collections.Generic.HashSet[string]]::new([StringComparer]::Ordinal)
$tipos = @('00-fundamentos-especificacion-oficial', '01-fundamentos-en-accion', '01-fundamentos', '02-estructuras-datos', '03-algoritmos', '04-patrones-diseno', '05-patrones-diseno-counter-strike')
foreach ($linea in (Get-Content -LiteralPath $catalogo -Encoding UTF8)) {
    if ([string]::IsNullOrWhiteSpace($linea) -or $linea.StartsWith('#')) { continue }
    $campos = $linea -split ([string][char]9), 2
    if ($campos.Count -ne 2) { throw "Fila inválida: $linea" }
    $paquete, $ruta = $campos
    if ($ruta.Contains('..') -or $ruta.Contains('\') -or $ruta.StartsWith('/')) {
        throw "Ruta de referencia insegura: $linea"
    }
    $partes = $ruta -split '/'
    # MVC entrypoint is intentionally at the root of the 05 practice
    # category; only this exact file is exempted from the usual depth rule.
    $integradores = @{
        java21 = '05-patrones-diseno-counter-strike/MainCounterStrike.java'
        python = '05-patrones-diseno-counter-strike/main.py'
        csharp = '05-patrones-diseno-counter-strike/MainCounterStrike.cs'
        cpp = '05-patrones-diseno-counter-strike/main.cpp'
        typescript = '05-patrones-diseno-counter-strike/MainCounterStrike.ts'
    }
    $integradorCS = ($integradores.ContainsKey($paquete) -and $ruta -eq $integradores[$paquete])
    if (($partes.Count -lt 3 -and -not $integradorCS) -or
        ($tipos -notcontains $partes[0] -and -not ($paquete -eq 'java21' -and $partes[0] -match '^(0[6-9]|1[0-9]|20)-'))) {
        throw "Archivo fuera de su carpeta de unidad: $linea"
    }
    $identificador = $paquete + '/' + $ruta
    if (-not $rutasVistas.Add($identificador)) {
        throw "Entrada duplicada: $identificador"
    }
    $destino = Join-Path $biblioteca ($identificador -replace '/', [IO.Path]::DirectorySeparatorChar)
    if (-not (Test-Path -LiteralPath $destino -PathType Leaf)) {
        throw "Archivo inexistente: $destino"
    }
    $cabecera = (Get-Content -LiteralPath $destino -Encoding UTF8 -TotalCount 12) -join [Environment]::NewLine
    if ($paquete -eq 'java21' -and ($ruta.StartsWith('00-fundamentos-especificacion-oficial/') -or $ruta -match '^(0[6-9]|1[0-9]|20)-')) {
        $contenido = Get-Content -LiteralPath $destino -Encoding UTF8 -Raw
        if ($contenido -notmatch '^// Tema:' -or $contenido -notmatch 'Compilar:' -or
            $contenido -notmatch 'Salida esperada:' -or $contenido -match 'Referencia: Java SE 21|Tema JLS:') {
            throw "Falta explicación o comando, o contiene referencia bibliográfica: $identificador"
        }
    } elseif ($cabecera -notmatch '(?i)codetrace' -or
        $cabecera -notmatch '(?i)(referencia|kotlin 2\.2)' -or
        $cabecera -notmatch '(?i)(compilar|comprobar|ejecutar|verificar|tipos/sintaxis)') {
        throw "Falta versión/comando de práctica: $identificador"
    }
    [void] $referencias.Add([pscustomobject]@{
        Lenguaje = $paquete
        Categoria = $partes[0]
        Ruta = $ruta
        Unidad = $paquete + '/' + ($ruta -replace '/[^/]+$', '')
    })
}

$idiomas = @($referencias.Lenguaje | Sort-Object -Unique)
if ($referencias.Count -ne 1892 -or $idiomas.Count -ne 14) {
    throw "Catálogo inconsistente: $($referencias.Count) archivos, $($idiomas.Count) lenguajes"
}
Write-Host "CodeTrace v27 — catálogo íntegro: $($referencias.Count) archivos / $($idiomas.Count) lenguajes"
foreach ($grupo in ($referencias | Group-Object Lenguaje | Sort-Object Name)) {
    $unidades = @($grupo.Group.Unidad | Sort-Object -Unique).Count
    Write-Host ("  {0,-16} {1,3} archivos  {2,3} carpetas con código" -f
        $grupo.Name, $grupo.Count, $unidades)
}

if (-not $EjecutarClientes) {
    Write-Host "AUDITORÍA ESTÁTICA OK (sin ejecutar ni modificar referencias)"
    exit 0
}

if (-not (Get-Command javac -ErrorAction SilentlyContinue) -or
    -not (Get-Command java -ErrorAction SilentlyContinue)) {
    throw "Para ejecutar clientes se necesitan Java y javac del JDK 21 en PATH"
}
$temporal = Join-Path ([IO.Path]::GetTempPath()) ("codetrace-grafos-" + [Guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Path $temporal -Force | Out-Null
try {
    $fuentes = @(Get-ChildItem -LiteralPath (Join-Path $biblioteca 'java21') -Recurse -Filter '*.java' -File |
        Where-Object { $_.FullName -notmatch '[\\/](?:00-fundamentos-especificacion-oficial|(?:0[6-9]|1[0-9]|20)-[^\\/]+)[\\/]' } |
        ForEach-Object { $_.FullName })
    & javac --release 21 -encoding UTF-8 -d $temporal $fuentes
    if ($LASTEXITCODE -ne 0) { throw 'Falló compilación Java 21' }
    foreach ($clase in @(
        'codetrace.practica.grafostipos.MainTiposGrafo',
        'codetrace.practica.grafosrepresentaciones.MainRepresentaciones',
        'codetrace.practica.grafosgenericos.MainGrafoGenerico',
        'codetrace.practica.recorridos.MainCaminosSinPeso',
        'codetrace.practica.grafoscaminos.MainCaminosMinimos',
        'codetrace.practica.grafoscaminos.MainCasosLimite',
        'codetrace.practica.grafosmst.MainExpansionMinima',
        'codetrace.practica.grafostopologico.MainTopologico',
        'codetrace.practica.grafosscc.MainComponentesFuertes',
        'MainFactoryBasica', 'MainObservadorBasico',
        'codetrace.practica.estructuras.listadoble.MainListaDoble',
        'codetrace.practica.estructuras.listacircular.MainListaCircular',
        'codetrace.practica.estructuras.listadoblecircular.MainListaDobleCircular',
        'codetrace.practica.estructuras.colaenlazada.MainColaEnlazada',
        'codetrace.practica.estructuras.deque.MainDeque',
        'codetrace.practica.estructuras.avl.MainArbolAVL',
        'codetrace.practica.estructuras.trie.MainTrie',
        'codetrace.practica.estructuras.unionfind.MainUnionFind',
        'codetrace.practica.estructuras.heapmaximo.MainHeapMaximo',
        'codetrace.practica.estructuras.hashabierto.MainHashAbierto',
        'codetrace.practica.estructuras.grafoponderado.MainGrafoPonderado',
        'codetrace.practica.patrones.builder.MainBuilder',
        'codetrace.practica.patrones.abstractfactory.MainAbstractFactory',
        'codetrace.practica.patrones.composite.MainComposite',
        'codetrace.practica.patrones.command.MainCommand',
        'codetrace.practica.patrones.templatemethod.MainTemplateMethod',
        'codetrace.practica.patrones.cadena.MainCadenaResponsabilidad',
        'codetrace.practica.counterstrike.MainCounterStrike',
        'codetrace.practica.counterstrike.cadena.MainCadenaCS',
        'codetrace.practica.counterstrike.observer.MainObserverCS',
        'codetrace.practica.counterstrike.strategy.MainStrategyCS',
        'codetrace.practica.counterstrike.command.MainCommandCS',
        'codetrace.practica.counterstrike.abstractfactory.MainAbstractFactoryCS',
        'codetrace.practica.counterstrike.adapter.MainAdapterCS',
        'codetrace.practica.counterstrike.builder.MainBuilderCS',
        'codetrace.practica.counterstrike.composite.MainCompositeCS',
        'codetrace.practica.counterstrike.decorator.MainDecoratorCS',
        'codetrace.practica.counterstrike.factory.MainFactoryCS',
        'codetrace.practica.counterstrike.factorymethod.MainFactoryMethodCS',
        'codetrace.practica.counterstrike.puntuacion.MainPuntuacionCS',
        'codetrace.practica.counterstrike.template.MainTemplateCS'
    )) {
        Write-Host ("Comprobando: " + $clase)
        & java -cp $temporal $clase
        if ($LASTEXITCODE -ne 0) { throw ("Cliente fallido: " + $clase) }
    }
    Write-Host 'AUDITORÍA ESTÁTICA Y EJECUCIÓN JAVA OK'
} finally {
    if (Test-Path -LiteralPath $temporal) {
        Remove-Item -LiteralPath $temporal -Recurse -Force
    }
}
