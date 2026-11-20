// CodeTrace | Referencia: Go 1.24
// Comprobar: go run ".\03-algoritmos\bfs-grafo\bfs_grafo.go"
// Ejecutar: go run ".\03-algoritmos\bfs-grafo\bfs_grafo.go"

package main
import "fmt"

/*
Cliente BFS de un grafo representado por map[string][]string.

La cola se recorre con índice, evitando desplazar elementos.
Un conjunto de visitados se simula con map[string]bool.
Con lista de adyacencia, complejidad O(V+E) tiempo, O(V) espacio.
*/
func bfs(g map[string][]string, origen string) []string {
    vistos := map[string]bool{origen:true}
    cola := []string{origen}
    salida := []string{}
    for i:=0; i<len(cola); i++ {
        v:=cola[i]
        salida=append(salida, v)
        for _, w:=range g[v] {
            if !vistos[w] { vistos[w]=true; cola=append(cola,w) }
        }
    }
    return salida
}
func main() {
    g:=map[string][]string{"A":{"B","C"}, "B":{"D"}, "C":{"E"}, "D":{}, "E":{}}
    fmt.Println(bfs(g, "A"))
}
