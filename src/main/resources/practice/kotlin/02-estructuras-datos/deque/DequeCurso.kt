// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Deque de doble extremo aprovechando ArrayDeque estándar de Kotlin.
 * Cuatro operaciones O(1) amortizado. La lista doble manual se
 * estudia en linked-list/doble, evitando duplicarla aquí.
 */
class DequeCurso<T>{
    private val datos=ArrayDeque<T>()
    fun insertarPrimero(v:T){datos.addFirst(v)}
    fun insertarUltimo(v:T){datos.addLast(v)}
    fun quitarPrimero():T=datos.removeFirst()
    fun quitarUltimo():T=datos.removeLast()
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\deque\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: DequeCurso.kt es un componente; la salida corresponde al cliente MainDeque.kt.
 * Salida esperada:
 *   Deque verificado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
