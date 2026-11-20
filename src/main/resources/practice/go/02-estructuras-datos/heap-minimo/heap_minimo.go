// CodeTrace | Referencia: Go 1.24
// Comprobar: go run ".\02-estructuras-datos\heap-minimo\heap_minimo.go"
// Ejecutar: go run ".\02-estructuras-datos\heap-minimo\heap_minimo.go"

package main
import (
    "container/heap"
    "fmt"
)

/*
Cliente de heap.Interface de la biblioteca estándar Go.

El contrato exige Len, Less, Swap, Push y Pop. heap.Push y
heap.Pop construyen la disciplina de prioridad usando el tipo
de usuario. Ambos cuestan O(log n), Peek O(1).
*/
type Prioridades []int
func (p Prioridades) Len() int { return len(p) }
func (p Prioridades) Less(i, j int) bool { return p[i] < p[j] }
func (p Prioridades) Swap(i, j int) { p[i], p[j] = p[j], p[i] }
func (p *Prioridades) Push(x any) { *p = append(*p, x.(int)) }
func (p *Prioridades) Pop() any {
    ultimo := len(*p)-1
    x := (*p)[ultimo]
    *p = (*p)[:ultimo]
    return x
}
func main() {
    q := &Prioridades{}
    heap.Init(q)
    for _, x := range []int{7, 1, 5, 2} { heap.Push(q, x) }
    for q.Len() > 0 { fmt.Println(heap.Pop(q)) }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   go run (Get-ChildItem .\02-estructuras-datos\heap-minimo\*.go).FullName
 * Salida esperada:
 *   1
 *   2
 *   5
 *   7
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
