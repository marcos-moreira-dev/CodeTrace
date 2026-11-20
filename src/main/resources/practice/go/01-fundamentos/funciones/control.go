// CodeTrace | Referencia: Go 1.24.
// Desde PowerShell, en una COPIA del paquete: 
// Compilar y ejecutar: go run '.\01-fundamentos/funciones\control.go'

package main

import "fmt"

func sumaPares(limite int) int {
    total := 0
    for i := 0; i <= limite; i++ {
        if i%2 == 0 { total += i }
    }
    return total
}

func main() {
    fmt.Println(sumaPares(10))
}
