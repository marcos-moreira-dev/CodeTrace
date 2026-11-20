// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\02-estructuras-datos\pila\Pila.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\pila\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: Pila.kt es un componente; la salida corresponde al cliente MainPila.kt.
 * Salida esperada:
 *   Pila LIFO verificada
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
