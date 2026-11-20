# CodeTrace | Referencia: PowerShell 7.4
# Comprobar: pwsh -NoProfile -File ".\01-fundamentos\json_y_objetos.ps1"
# Ejecutar: pwsh -NoProfile -File ".\01-fundamentos\json_y_objetos.ps1"

<#
Cliente de JSON, PSCustomObject y objetos del pipeline.

PowerShell mueve objetos entre cmdlets, no solo cadenas de texto.
ConvertFrom-Json produce un objeto consultable por propiedades,
y ConvertTo-Json vuelve a serializarlo. Se utiliza texto en memoria
para no escribir sobre los recursos de práctica de CodeTrace.
#>
$json = '{"codigo":"S-01","precios":[4.5,7.2,3.0]}'
$pedido = $json | ConvertFrom-Json
$total = ($pedido.precios | Measure-Object -Sum).Sum
$resultado = [pscustomobject]@{ Codigo = $pedido.codigo; Total = $total }
$resultado | ConvertTo-Json -Compress
