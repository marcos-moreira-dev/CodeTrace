// CodeTrace | Referencia: Go 1.24.
// Desde PowerShell, en una COPIA del paquete: 
// Compilar y ejecutar: go run '.\01-fundamentos/funciones\control.go'

package main

import "fmt"

func sumaPares(limite int) int {
    total := 0
    for i := 0; i <= limite; i++ {
        if i%2 == 0 { total += i }
    }
    return total
}

func main() {
    fmt.Println(sumaPares(10))
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   go run (Get-ChildItem .\01-fundamentos\funciones\*.go).FullName
 * Salida esperada:
 *   30
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
