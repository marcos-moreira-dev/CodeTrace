# CodeTrace | Referencia: PowerShell 7.4
# Comprobar: pwsh -NoProfile -File ".\03-algoritmos\frecuencias_objetos.ps1"
# Ejecutar: pwsh -NoProfile -File ".\03-algoritmos\frecuencias_objetos.ps1"

<#
Cliente de frecuencia de valores mediante Group-Object.

Primero se construyen grupos de cadenas; después se selecciona
su nombre y cantidad. El pipeline expresa un proceso de datos
sin implementar un árbol o un comparador artificial en PowerShell.
El costo concreto depende de la implementación de Group-Object.
#>
$palabras = @('red', 'datos', 'red', 'algoritmo', 'datos', 'red')
$palabras |
    Group-Object |
    Sort-Object Count -Descending |
    Select-Object Name, Count |
    Format-Table -AutoSize
