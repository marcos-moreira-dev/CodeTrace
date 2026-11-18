function Sumar-Pares {
    param([int]$Limite)
    $total = 0
    for ($i = 0; $i -le $Limite; $i++) {
        if ($i % 2 -eq 0) { $total += $i }
    }
    return $total
}

Write-Host (Sumar-Pares -Limite 10)
