# CodeTrace | Referencia: PowerShell 7.4.
# En una COPIA del paquete, desde PowerShell o shell indicado:
# Ejecutar: pwsh -NoProfile -File '.\02-estructuras-datos\pila\pila.ps1'

$pila = [System.Collections.Generic.Stack[int]]::new()
$pila.Push(12)
$pila.Push(8)
Write-Host ("Desapilado: " + $pila.Pop())

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   pwsh -NoProfile -File .\02-estructuras-datos\pila\pila.ps1
# Salida esperada:
#   Desapilado: 8
# CodeTrace | FIN EJEMPLO DE CONSOLA
