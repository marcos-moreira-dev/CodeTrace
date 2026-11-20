// CodeTrace | Referencia: Go 1.24
// Comprobar: go run ".\01-fundamentos\slices-y-capacidad\slices_y_capacidad.go"
// Ejecutar: go run ".\01-fundamentos\slices-y-capacidad\slices_y_capacidad.go"

package main
import "fmt"

/*
Cliente de slices: longitud, capacidad y arreglo subyacente.

Un slice es una vista sobre almacenamiento que puede compartirse
con otros slices. append reutiliza el arreglo si hay capacidad,
o asigna otro si debe crecer. Copiar explícitamente separa los
datos cuando no se desea aliasing.
*/
func main() {
    numeros := make([]int, 2, 4)
    numeros[0], numeros[1] = 10, 20
    compartida := numeros[:1]
    compartida[0] = 99
    copia := append([]int(nil), numeros...)
    copia[0] = 7
    fmt.Printf("original=%v copia=%v cap=%d\n", numeros, copia, cap(numeros))
}
