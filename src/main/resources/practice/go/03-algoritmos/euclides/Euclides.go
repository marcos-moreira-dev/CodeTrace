// CodeTrace | Referencia: Go 1.24.
// Desde PowerShell en una COPIA del paquete:
// Compilar y ejecutar: go run '.\03-algoritmos\euclides\Euclides.go'

package main
import "fmt"
func mcd(a, b int) int {
    for b != 0 { a, b = b, a%b }
    return a
}
func main() { fmt.Println("MCD:", mcd(48, 18)) }
