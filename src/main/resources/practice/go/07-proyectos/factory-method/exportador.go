// CodeTrace | Referencia: Go 1.23+, Factory Method por interfaces
// Compilar: go run exportador.go fabrica.go main.go
package main
type Exportador interface {
    Exportar(contenido string) string
}
type ExportadorTexto struct{}
func (e ExportadorTexto) Exportar(contenido string) string {
    return "TEXTO: " + contenido
}
