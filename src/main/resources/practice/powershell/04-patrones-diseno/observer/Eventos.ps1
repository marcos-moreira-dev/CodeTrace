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
