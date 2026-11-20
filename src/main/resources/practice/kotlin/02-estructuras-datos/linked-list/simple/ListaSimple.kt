// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\02-estructuras-datos\linked-list\simple\ListaSimple.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

class ListaSimple<T> {
    private class Nodo<T>(val dato: T, var siguiente: Nodo<T>? = null)

    private var primero: Nodo<T>? = null
    var cantidad: Int = 0
        private set

    fun agregar(valor: T) {
        primero = Nodo(valor, primero)
        cantidad++
    }

    fun obtenerPrimero(): T =
        primero?.dato ?: throw NoSuchElementException("Lista vacía")
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\linked-list\simple\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: ListaSimple.kt es un componente; la salida corresponde al cliente MainListaSimple.kt.
 * Salida esperada:
 *   Primero: Estructuras
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
