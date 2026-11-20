# CodeTrace | Referencia: PowerShell 7+, callbacks en dos archivos
# Compilar: pwsh -NoProfile -File 07-proyectos/observer/main.ps1
. "$PSScriptRoot/Eventos.ps1"
Registrar-Observador -Handler {
    param([string]$evento)
    Write-Host "Evento: $evento"
}
Publicar-Evento -Evento "Factura 001"
