// CodeTrace | Referencia: Go 1.23+, Factory Method por interfaces
// Compilar: go run exportador.go fabrica.go main.go
package main
type Fabrica interface { Crear() Exportador }
type FabricaTexto struct{}
func (f FabricaTexto) Crear() Exportador { return ExportadorTexto{} }
