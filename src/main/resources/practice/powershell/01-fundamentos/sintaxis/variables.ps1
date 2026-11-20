# CodeTrace | Referencia: PowerShell 7.4.
# Desde PowerShell en una COPIA de este paquete:
# Ejecutar: pwsh -NoProfile -File '.\01-fundamentos/sintaxis\variables.ps1'

$nombre = "CodeTrace"
$cantidad = 3
$precio = 12.5
$total = $cantidad * $precio
Write-Host ($nombre + ": " + $total)

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   pwsh -NoProfile -File .\01-fundamentos\sintaxis\variables.ps1
# Salida esperada:
#   CodeTrace: 37.5
# CodeTrace | FIN EJEMPLO DE CONSOLA
