// CodeTrace | Referencia: Go 1.24
// Compilar/Comprobar: go run 01-fundamentos/goroutines-canales/goroutines_canales.go
// Ejecutar: go run 01-fundamentos/goroutines-canales/goroutines_canales.go

package main
import "fmt"
/*
Cliente productor-consumidor de Go.
La goroutine entrega enteros en un canal tipado; close marca el final.
El consumidor usa range y no necesita sleeps ni suponer un orden del
planificador. El canal sirve de mecanismo de comunicación.
*/
func producir(salida chan<- int) {
    defer close(salida)
    for _, n := range []int{2, 3, 5, 7} { salida <- n }
}
func main() {
    canal := make(chan int)
    go producir(canal)
    for n := range canal { fmt.Println(n) }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   go run (Get-ChildItem .\01-fundamentos\goroutines-canales\*.go).FullName
 * Salida esperada:
 *   2
 *   3
 *   5
 *   7
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
