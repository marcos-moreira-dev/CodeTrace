// CodeTrace | Referencia: Go 1.23+, Factory Method por interfaces
// Compilar: go run exportador.go fabrica.go main.go
package main
import "fmt"
func main() {
    var fabrica Fabrica = FabricaTexto{}
    fmt.Println(fabrica.Crear().Exportar("Factura 001"))
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   go run (Get-ChildItem .\04-patrones-diseno\factory-method\*.go).FullName
 * Salida esperada:
 *   TEXTO: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
