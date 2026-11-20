# CodeTrace | Referencia: PowerShell 7.4.
# En una COPIA del paquete, desde PowerShell o shell indicado:
# Ejecutar: pwsh -NoProfile -File '.\02-estructuras-datos\pila\pila.ps1'

$pila = [System.Collections.Generic.Stack[int]]::new()
$pila.Push(12)
$pila.Push(8)
Write-Host ("Desapilado: " + $pila.Pop())
