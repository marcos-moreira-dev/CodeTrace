// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin-unit.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin-unit.jar"
// El cliente Main... es el único punto de entrada en cada unidad.

/**
 * Cliente: pila LIFO basada en ArrayDeque de Kotlin.
 *
 * Las operaciones son O(1) amortizado; no se necesita acceso por
 * índice para recuperar el último elemento insertado. Compara
 * esta disciplina con FIFO en MainColaCircular.
 */
fun main() {
    val pila = Pila<String>()
    listOf("A", "B", "C").forEach(pila::apilar)
    check(pila.desapilar() == "C" && pila.desapilar() == "B")
    check(pila.cantidad() == 1)
    println("Pila LIFO verificada")
}
