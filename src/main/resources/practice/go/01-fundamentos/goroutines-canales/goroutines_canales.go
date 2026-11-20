// CodeTrace | Referencia: Go 1.24
// Compilar/Comprobar: go run ARCHIVO.go
// Ejecutar: go run ARCHIVO.go

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
