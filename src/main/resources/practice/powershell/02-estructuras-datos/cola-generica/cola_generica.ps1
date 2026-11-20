# CodeTrace | Referencia: PowerShell 7.4
# Comprobar: pwsh -NoProfile -File ".\02-estructuras-datos\cola-generica\cola_generica.ps1"
# Ejecutar: pwsh -NoProfile -File ".\02-estructuras-datos\cola-generica\cola_generica.ps1"

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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   pwsh -NoProfile -File .\02-estructuras-datos\cola-generica\cola_generica.ps1
# Salida esperada:
#   Trabajo
#   -------
#   factura
#   correo
#   respaldo
# CodeTrace | FIN EJEMPLO DE CONSOLA
