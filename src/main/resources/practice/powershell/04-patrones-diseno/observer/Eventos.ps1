# CodeTrace | Referencia: PowerShell 7+, callbacks en dos archivos
# Compilar: pwsh -NoProfile -File 04-patrones-diseno/observer/main.ps1
$script:Handlers = @()
function Registrar-Observador {
    param([scriptblock]$Handler)
    $script:Handlers += $Handler
}
function Publicar-Evento {
    param([string]$Evento)
    foreach ($handler in $script:Handlers) { & $handler $Evento }
}

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   pwsh -NoProfile -File .\04-patrones-diseno\observer\main.ps1
# Nota: Eventos.ps1 es un componente; la salida corresponde al cliente main.ps1.
# Salida esperada:
#   Evento: Factura 001
# CodeTrace | FIN EJEMPLO DE CONSOLA
