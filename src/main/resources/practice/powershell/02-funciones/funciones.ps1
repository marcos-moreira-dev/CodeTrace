# CodeTrace | Referencia: PowerShell 7.4.
# Desde PowerShell en una COPIA de este paquete:
# Ejecutar: pwsh -NoProfile -File '.\02-funciones\funciones.ps1'

function Sumar-Pares {
    param([int]$Limite)
    $total = 0
    for ($i = 0; $i -le $Limite; $i++) {
        if ($i % 2 -eq 0) { $total += $i }
    }
    return $total
}

Write-Host (Sumar-Pares -Limite 10)
