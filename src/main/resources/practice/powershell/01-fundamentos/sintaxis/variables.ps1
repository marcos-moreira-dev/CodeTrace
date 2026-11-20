# CodeTrace | Referencia: PowerShell 7.4.
# Desde PowerShell en una COPIA de este paquete:
# Ejecutar: pwsh -NoProfile -File '.\01-sintaxis\variables.ps1'

$nombre = "CodeTrace"
$cantidad = 3
$precio = 12.5
$total = $cantidad * $precio
Write-Host ($nombre + ": " + $total)
