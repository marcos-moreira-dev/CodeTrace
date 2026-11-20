// CodeTrace | Referencia: Go 1.24.
// Desde PowerShell en una COPIA del paquete:
// Compilar y ejecutar: go run '.\02-estructuras-datos\pila\Pila.go'

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   go run (Get-ChildItem .\02-estructuras-datos\pila\*.go).FullName
 * Salida esperada:
 *   8
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
