// CodeTrace | Referencia: Go 1.24.
// Desde PowerShell en una COPIA del paquete:
// Compilar y ejecutar: go run '.\04-patrones-diseno\observer\basico\Observer.go'

package main
import "fmt"
type Observador func(int)
type Emisor struct { oyentes []Observador }
func (e *Emisor) Suscribir(listener Observador) {
    e.oyentes = append(e.oyentes, listener)
}
func (e *Emisor) Actualizar(valor int) {
    for _, listener := range e.oyentes { listener(valor) }
}
func main() {
    e := &Emisor{}
    e.Suscribir(func(v int) { fmt.Println("Cambio:", v) })
    e.Actualizar(42)
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   go run (Get-ChildItem .\04-patrones-diseno\observer\basico\*.go).FullName
 * Salida esperada:
 *   Cambio: 42
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
