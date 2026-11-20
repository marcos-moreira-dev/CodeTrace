// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en copia desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-unidad.jar"
// Ejecutar: java -jar "$env:TEMP\ct-unidad.jar"
// Contrato: una unidad conserva cliente Main... y clases relacionadas.

/**
 * Tabla hash educativa con buckets de pares clave-valor.
 *
 * Un índice calculado con hashCode no identifica una clave de
 * forma exclusiva: se debe comparar la clave original. Usa
 * encadenamiento para colisiones. Esperado O(1) si los hashes
 * se distribuyen bien; peor O(n), sin redimensionado automático.
 */
class TablaHash<V>(capacidad: Int) {
    private val cubetas: Array<MutableList<Pair<String, V>>>

    init {
        require(capacidad > 0)
        cubetas = Array(capacidad) { mutableListOf() }
    }

    private fun indice(clave: String) =
        Math.floorMod(clave.hashCode(), cubetas.size)

    fun poner(clave: String, valor: V) {
        val lista = cubetas[indice(clave)]
        val pos = lista.indexOfFirst { it.first == clave }
        if (pos >= 0) lista[pos] = clave to valor
        else lista.add(clave to valor)
    }

    fun obtener(clave: String): V? =
        cubetas[indice(clave)].firstOrNull { it.first == clave }?.second
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\tabla-hash\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: TablaHash.kt es un componente; la salida corresponde al cliente MainTablaHash.kt.
 * Salida esperada:
 *   Tabla hash: A=3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
