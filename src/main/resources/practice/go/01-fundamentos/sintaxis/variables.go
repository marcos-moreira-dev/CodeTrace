// CodeTrace | Referencia: Go 1.24.
// Desde PowerShell, en una COPIA del paquete: 
// Compilar y ejecutar: go run '.\01-fundamentos/sintaxis\variables.go'

package main

import "fmt"

func main() {
    cantidad := 4
    precio := 12.5
    fmt.Println(float64(cantidad) * precio)
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   go run (Get-ChildItem .\01-fundamentos\sintaxis\*.go).FullName
 * Salida esperada:
 *   50
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
