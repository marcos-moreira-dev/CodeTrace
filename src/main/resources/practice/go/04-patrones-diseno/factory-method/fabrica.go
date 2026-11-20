// CodeTrace | Referencia: Go 1.23+, Factory Method por interfaces
// Compilar: go run exportador.go fabrica.go main.go
package main
type Fabrica interface { Crear() Exportador }
type FabricaTexto struct{}
func (f FabricaTexto) Crear() Exportador { return ExportadorTexto{} }

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   go run (Get-ChildItem .\04-patrones-diseno\factory-method\*.go).FullName
 * Nota: fabrica.go es un componente; la salida corresponde al cliente main.go.
 * Salida esperada:
 *   TEXTO: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
