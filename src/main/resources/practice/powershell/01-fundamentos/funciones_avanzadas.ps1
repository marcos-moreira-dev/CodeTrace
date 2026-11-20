# CodeTrace | Referencia: PowerShell 7.4
# Comprobar: pwsh -NoProfile -File ARCHIVO.ps1
# Ejecutar: pwsh -NoProfile -File ARCHIVO.ps1

<#
Cliente de parámetros tipados, validación y funciones avanzadas.

CmdletBinding incorpora semántica de cmdlets. ValidateRange protege
precondiciones. El resultado se entrega al pipeline como objeto y no
se imprime dentro de la función, permitiendo composición.
#>
function Get-PrecioFinal {
    [CmdletBinding()]
    param(
        [Parameter(Mandatory)]
        [ValidateRange(0, 100000)]
        [double] $Subtotal,
        [ValidateRange(0, 1)]
        [double] $Descuento = 0
    )
    return [math]::Round($Subtotal * (1 - $Descuento), 2)
}
Get-PrecioFinal -Subtotal 120 -Descuento 0.15
