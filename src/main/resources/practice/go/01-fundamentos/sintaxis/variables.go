// CodeTrace | Referencia: Go 1.24.
// Desde PowerShell, en una COPIA del paquete: 
// Compilar y ejecutar: go run '.\01-fundamentos/sintaxis\variables.go'

package main

import "fmt"

func main() {
    cantidad := 4
    precio := 12.5
    fmt.Println(float64(cantidad) * precio)
}
