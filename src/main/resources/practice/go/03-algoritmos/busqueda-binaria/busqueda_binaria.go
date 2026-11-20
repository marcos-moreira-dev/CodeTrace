// CodeTrace | Referencia: Go 1.24
// Compilar/Comprobar: go run ARCHIVO.go
// Ejecutar: go run ARCHIVO.go

package main
import "fmt"
/*
Cliente de búsqueda binaria con slice de enteros ordenado.
El intervalo semiabierto [inicio, fin) se reduce progresivamente.
La complejidad es O(log n) tiempo, O(1) memoria auxiliar.
*/
func buscar(datos []int, objetivo int) int {
    lo, hi := 0, len(datos)
    for lo < hi {
        mid := lo + (hi-lo)/2
        if datos[mid] == objetivo { return mid }
        if datos[mid] < objetivo { lo = mid + 1 } else { hi = mid }
    }
    return -1
}
func main() { fmt.Println(buscar([]int{2, 4, 8, 16, 32}, 16)) }
