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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   go run (Get-ChildItem .\03-algoritmos\euclides\*.go).FullName
 * Salida esperada:
 *   MCD: 6
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
