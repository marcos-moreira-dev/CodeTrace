// CodeTrace | Referencia: Go 1.24
// Comprobar: go run ".\01-fundamentos\errores_y_defer.go"
// Ejecutar: go run ".\01-fundamentos\errores_y_defer.go"

package main
import (
    "errors"
    "fmt"
)

/*
Cliente de errores explícitos, defer y retorno múltiple en Go.

Go comunica resultados y fallos como valores. El llamador comprueba
el error en lugar de depender de excepciones; defer registra limpieza
para el final del ámbito de función. errors.Is compara errores
identificables sin depender de textos de mensajes.
*/
var ErrDivisor = errors.New("divisor cero")
func dividir(a, b int) (int, error) {
    if b == 0 { return 0, ErrDivisor }
    return a / b, nil
}
func main() {
    defer fmt.Println("Fin del cliente")
    if valor, err := dividir(12, 3); err == nil {
        fmt.Println("Cociente:", valor)
    }
    if _, err := dividir(3, 0); errors.Is(err, ErrDivisor) {
        fmt.Println("Error controlado:", err)
    }
}
