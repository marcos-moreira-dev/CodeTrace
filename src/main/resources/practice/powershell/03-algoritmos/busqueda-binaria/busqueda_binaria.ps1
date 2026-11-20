# CodeTrace | Referencia: PowerShell 7.4
# Comprobar: pwsh -NoProfile -File 03-algoritmos/busqueda-binaria/busqueda_binaria.ps1
# Ejecutar: pwsh -NoProfile -File 03-algoritmos/busqueda-binaria/busqueda_binaria.ps1

<#
Cliente de búsqueda binaria en una secuencia numérica ordenada.

El algoritmo conserva un intervalo semiabierto de candidatos.
Cada comparación descarta media región, por lo que su costo es
O(log n) tiempo y O(1) espacio. No confundir este algoritmo
con Where-Object, cuyo filtrado secuencial cuesta O(n).
#>
function Find-Binary {
    param([int[]] $Datos, [int] $Objetivo)
    $inicio = 0
    $fin = $Datos.Count
    while ($inicio -lt $fin) {
        $mitad = $inicio + [int][math]::Floor(($fin - $inicio) / 2)
        if ($Datos[$mitad] -eq $Objetivo) { return $mitad }
        if ($Datos[$mitad] -lt $Objetivo) { $inicio = $mitad + 1 }
        else { $fin = $mitad }
    }
    return -1
}
Find-Binary -Datos @(2, 5, 8, 12, 21) -Objetivo 12
