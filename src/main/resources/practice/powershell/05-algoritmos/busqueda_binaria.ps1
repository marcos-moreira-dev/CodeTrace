# CodeTrace | Referencia: PowerShell 7.4.
# En una COPIA del paquete, desde PowerShell o shell indicado:
# Ejecutar: pwsh -NoProfile -File '.\05-algoritmos\busqueda_binaria.ps1'

function Find-Numero {
    param([int[]]$Datos, [int]$Objetivo)
    $izquierda = 0; $derecha = $Datos.Count - 1
    while ($izquierda -le $derecha) {
        $medio = [int][math]::Floor(($izquierda + $derecha) / 2)
        if ($Datos[$medio] -eq $Objetivo) { return $medio }
        if ($Datos[$medio] -lt $Objetivo) { $izquierda = $medio + 1 }
        else { $derecha = $medio - 1 }
    }
    return -1
}
Write-Host (Find-Numero -Datos @(2,4,8,12,16) -Objetivo 12)
