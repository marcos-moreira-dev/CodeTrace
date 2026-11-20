// CodeTrace | Referencia: Go 1.23+, Factory Method por interfaces
// Compilar: go run exportador.go fabrica.go main.go
package main
import "fmt"
func main() {
    var fabrica Fabrica = FabricaTexto{}
    fmt.Println(fabrica.Crear().Exportar("Factura 001"))
}

// Salida esperada de consola:
//   Se imprime el valor o coleccion calculado por el cliente.
// Verificacion: termina sin excepciones inesperadas.
