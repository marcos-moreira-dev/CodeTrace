# CodeTrace | Referencia: PowerShell 7.4
# Comprobar: pwsh -NoProfile -File ".\02-estructuras-datos\cola_generica.ps1"
# Ejecutar: pwsh -NoProfile -File ".\02-estructuras-datos\cola_generica.ps1"

<#
Cliente de una cola FIFO .NET desde PowerShell 7.4.

System.Collections.Generic.Queue[T] permite Enqueue/Dequeue y elimina
la necesidad de desplazar arreglos manualmente. Estas operaciones son
O(1) amortizado. Se conserva el pipeline para presentar resultados.
#>
$cola = [System.Collections.Generic.Queue[string]]::new()
$cola.Enqueue('factura')
$cola.Enqueue('correo')
$cola.Enqueue('respaldo')
while ($cola.Count -gt 0) {
    [pscustomobject]@{ Trabajo = $cola.Dequeue() }
}
