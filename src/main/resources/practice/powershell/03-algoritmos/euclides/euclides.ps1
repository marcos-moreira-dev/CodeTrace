# CodeTrace | Referencia: PowerShell 7.4.
# En una COPIA del paquete, desde PowerShell o shell indicado:
# Ejecutar: pwsh -NoProfile -File '.\03-algoritmos\euclides\euclides.ps1'

function Get-Mcd {
    param([int]$A, [int]$B)
    while ($B -ne 0) {
        $resto = $A % $B
        $A = $B
        $B = $resto
    }
    return $A
}
Write-Host ("MCD: " + (Get-Mcd -A 48 -B 18))

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   pwsh -NoProfile -File .\03-algoritmos\euclides\euclides.ps1
# Salida esperada:
#   MCD: 6
# CodeTrace | FIN EJEMPLO DE CONSOLA
