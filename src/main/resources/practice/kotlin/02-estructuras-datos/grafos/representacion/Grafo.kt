// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en copia desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-unidad.jar"
// Ejecutar: java -jar "$env:TEMP\ct-unidad.jar"
// Contrato: una unidad conserva cliente Main... y clases relacionadas.

/**
 * Grafo dirigido y ponderado almacenado en listas de adyacencia.
 *
 * El coste en memoria es O(V+E), razonable si hay pocas aristas
 * frente a V². Un arco A->B no implica B->A. Los valores son pesos
 * finitos; para Dijkstra se necesitan además no negativos.
 */
class Grafo {
    private val adj = linkedMapOf<String, MutableMap<String, Double>>()

    fun vertice(id: String) {
        require(id.isNotBlank())
        adj.putIfAbsent(id, linkedMapOf())
    }

    fun conectar(origen: String, destino: String, peso: Double) {
        require(origen != destino && peso.isFinite())
        vertice(origen)
        vertice(destino)
        adj.getValue(origen)[destino] = peso
    }

    fun vecinos(id: String): Map<String, Double> = adj.getValue(id).toMap()
    fun vertices(): Set<String> = adj.keys.toSet()
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\grafos\representacion\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: Grafo.kt es un componente; la salida corresponde al cliente MainGrafo.kt.
 * Salida esperada:
 *   Desde Guayaquil: {UIO=12.0, CUE=18.0}
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
