# CodeTrace | Referencia: PowerShell 7.4.
# Desde PowerShell en una COPIA de este paquete:
# Ejecutar: pwsh -NoProfile -File '.\01-fundamentos/funciones\funciones.ps1'

function Sumar-Pares {
    param([int]$Limite)
    $total = 0
    for ($i = 0; $i -le $Limite; $i++) {
        if ($i % 2 -eq 0) { $total += $i }
    }
    return $total
}

Write-Host (Sumar-Pares -Limite 10)

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   pwsh -NoProfile -File .\01-fundamentos\funciones\funciones.ps1
# Salida esperada:
#   30
# CodeTrace | FIN EJEMPLO DE CONSOLA
