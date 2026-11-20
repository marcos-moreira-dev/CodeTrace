# CodeTrace | Referencia: PowerShell 7.4.
# En una COPIA del paquete, desde PowerShell o shell indicado:
# Ejecutar: pwsh -NoProfile -File '.\04-patrones-diseno\callback\funciones\callback.ps1'

function Invoke-Cambio {
    param([int]$Valor, [scriptblock]$Escucha)
    & $Escucha $Valor
}
$observador = { param($Nuevo) Write-Host ("Cambio: " + $Nuevo) }
Invoke-Cambio -Valor 42 -Escucha $observador

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   pwsh -NoProfile -File .\04-patrones-diseno\callback\funciones\callback.ps1
# Salida esperada:
#   Cambio: 42
# CodeTrace | FIN EJEMPLO DE CONSOLA
