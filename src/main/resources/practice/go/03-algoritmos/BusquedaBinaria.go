// CodeTrace | Referencia: Go 1.24.
// Desde PowerShell en una COPIA del paquete:
// Compilar y ejecutar: go run '.\03-algoritmos\BusquedaBinaria.go'

package main
import "fmt"
func buscar(datos []int, objetivo int) int {
    izquierda, derecha := 0, len(datos)-1
    for izquierda <= derecha {
        medio := izquierda + (derecha-izquierda)/2
        if datos[medio] == objetivo { return medio }
        if datos[medio] < objetivo { izquierda = medio+1 } else { derecha = medio-1 }
    }
    return -1
}
func main() { fmt.Println(buscar([]int{2,4,8,12,16}, 12)) }
