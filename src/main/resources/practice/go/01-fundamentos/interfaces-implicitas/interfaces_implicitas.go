// CodeTrace | Referencia: Go 1.24
// Compilar/Comprobar: go run 01-fundamentos/interfaces-implicitas/interfaces_implicitas.go
// Ejecutar: go run 01-fundamentos/interfaces-implicitas/interfaces_implicitas.go

package main
import "fmt"
/*
Cliente de interfaces implícitas y composición.
Un tipo satisface una interfaz por tener sus métodos, sin escribir
implements. Así los consumidores definen contratos pequeños que
simplifican los cambios de implementación.
*/
type Describible interface { Describir() string }
type Sensor struct { Codigo string }
func (s Sensor) Describir() string { return "Sensor " + s.Codigo }
func mostrar(x Describible) { fmt.Println(x.Describir()) }
func main() { mostrar(Sensor{Codigo: "LM35"}) }

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   go run (Get-ChildItem .\01-fundamentos\interfaces-implicitas\*.go).FullName
 * Salida esperada:
 *   Sensor LM35
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
