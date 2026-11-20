# CodeTrace | Referencia: PowerShell 7.4
# Comprobar: pwsh -NoProfile -File ARCHIVO.ps1
# Ejecutar: pwsh -NoProfile -File ARCHIVO.ps1

<#
Cliente de filtrado y ordenamiento del pipeline de PowerShell.

A diferencia del shell de texto, los cmdlets intercambian objetos con
propiedades. Where-Object utiliza un predicado, Sort-Object un criterio
de orden; Select-Object produce vistas con propiedades específicas.
El ejemplo no consulta ni cambia procesos reales.
#>
$productos = @(
    [pscustomobject]@{ Codigo = 'C'; Precio = 2.50 }
    [pscustomobject]@{ Codigo = 'A'; Precio = 1.00 }
    [pscustomobject]@{ Codigo = 'B'; Precio = 1.00 }
)
$baratos = $productos |
    Where-Object { $_.Precio -lt 3 } |
    Sort-Object Precio, Codigo |
    Select-Object Codigo, Precio
$baratos | Format-Table -AutoSize
