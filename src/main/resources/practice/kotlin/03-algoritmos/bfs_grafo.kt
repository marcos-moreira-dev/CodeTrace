// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar (PowerShell): kotlinc -jvm-target 21 '.\03-algoritmos\bfs_grafo.kt' -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar"

import java.util.ArrayDeque

/**
 * Cliente de BFS: cola FIFO y conjunto de vértices visitados.
 *
 * Sobre listas de adyacencia, explorar un grafo cuesta O(V+E) tiempo
 * y O(V) memoria. Un vértice se marca cuando entra a la cola,
 * evitando visitas repetidas incluso en ciclos.
 */
fun bfs(g: Map<String, List<String>>, origen: String): List<String> {
    require(g.containsKey(origen))
    val cola = ArrayDeque<String>()
    val visto = mutableSetOf(origen)
    val salida = mutableListOf<String>()
    cola.addLast(origen)
    while (cola.isNotEmpty()) {
        val actual = cola.removeFirst()
        salida.add(actual)
        for (vecino in g[actual].orEmpty())
            if (visto.add(vecino)) cola.addLast(vecino)
    }
    return salida
}
fun main() {
    val grafo = mapOf("A" to listOf("B", "C"), "B" to listOf("D"),
        "C" to listOf("E"), "D" to emptyList(), "E" to emptyList())
    println(bfs(grafo, "A"))
}
