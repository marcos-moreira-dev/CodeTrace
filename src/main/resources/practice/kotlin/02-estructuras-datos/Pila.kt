// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\02-estructuras-datos\Pila.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

class Pila<T> {
    private val datos = ArrayDeque<T>()

    fun apilar(valor: T) {
        datos.addLast(valor)
    }

    fun desapilar(): T =
        if (datos.isEmpty()) throw NoSuchElementException("Pila vacía")
        else datos.removeLast()

    fun cantidad(): Int = datos.size
}
