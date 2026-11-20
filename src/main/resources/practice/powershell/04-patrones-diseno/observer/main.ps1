# CodeTrace | Referencia: PowerShell 7+, callbacks en dos archivos
# Compilar: pwsh -NoProfile -File 04-patrones-diseno/observer/main.ps1
. "$PSScriptRoot/Eventos.ps1"
Registrar-Observador -Handler {
    param([string]$evento)
    Write-Host "Evento: $evento"
}
Publicar-Evento -Evento "Factura 001"

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   pwsh -NoProfile -File .\04-patrones-diseno\observer\main.ps1
# Salida esperada:
#   Evento: Factura 001
# CodeTrace | FIN EJEMPLO DE CONSOLA
