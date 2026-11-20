// CodeTrace | Referencia: Go 1.24
// Compilar/Comprobar: go run 03-algoritmos/busqueda-binaria/busqueda_binaria.go
// Ejecutar: go run 03-algoritmos/busqueda-binaria/busqueda_binaria.go

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   go run (Get-ChildItem .\03-algoritmos\busqueda-binaria\*.go).FullName
 * Salida esperada:
 *   3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
