# CodeTrace | Referencia: PowerShell 7.4.
# En una COPIA del paquete, desde PowerShell o shell indicado:
# Ejecutar: pwsh -NoProfile -File '.\03-algoritmos\euclides.ps1'

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
