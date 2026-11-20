// CodeTrace | Referencia: Go 1.24
// Comprobar: go run ".\03-algoritmos\mochila_01.go"
// Ejecutar: go run ".\03-algoritmos\mochila_01.go"

package main
import "fmt"

/*
Cliente de mochila 0/1 con programación dinámica.

La capacidad recorre valores descendentes para que cada artículo
se use a lo sumo una vez. Tiempo O(n*C), memoria O(C), donde C
es la capacidad numérica, no su longitud en bits.
*/
func mochila(pesos, valores []int, capacidad int) int {
    if len(pesos) != len(valores) || capacidad < 0 { panic("Entrada") }
    dp:=make([]int, capacidad+1)
    for i, peso:=range pesos {
        if peso<=0 || valores[i]<0 { panic("Artículo") }
        for c:=capacidad; c>=peso; c-- {
            candidato:=dp[c-peso]+valores[i]
            if candidato>dp[c] { dp[c]=candidato }
        }
    }
    return dp[capacidad]
}
func main() { fmt.Println(mochila([]int{2,3,4,5}, []int{3,4,5,8}, 8)) }
