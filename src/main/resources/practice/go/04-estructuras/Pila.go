// CodeTrace | Referencia: Go 1.24.
// Desde PowerShell en una COPIA del paquete:
// Compilar y ejecutar: go run '.\04-estructuras\Pila.go'

package main
import "fmt"
type Pila struct { datos []int }
func (p *Pila) Apilar(valor int) { p.datos = append(p.datos, valor) }
func (p *Pila) Desapilar() (int, bool) {
    if len(p.datos) == 0 { return 0, false }
    ultimo := len(p.datos) - 1
    valor := p.datos[ultimo]
    p.datos = p.datos[:ultimo]
    return valor, true
}
func main() {
    pila := &Pila{}
    pila.Apilar(12)
    pila.Apilar(8)
    valor, ok := pila.Desapilar()
    if ok { fmt.Println(valor) }
}
