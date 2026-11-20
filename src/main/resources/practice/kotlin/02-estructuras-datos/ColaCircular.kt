// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\02-estructuras-datos\ColaCircular.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

class ColaCircular(capacidad: Int) {
    private val datos: IntArray
    private var inicio = 0
    private var siguiente = 0
    private var cantidad = 0

    init {
        require(capacidad > 0) { "Capacidad inválida" }
        datos = IntArray(capacidad)
    }

    fun encolar(valor: Int) {
        check(cantidad < datos.size) { "Cola llena" }
        datos[siguiente] = valor
        siguiente = (siguiente + 1) % datos.size
        cantidad++
    }

    fun desencolar(): Int {
        check(cantidad > 0) { "Cola vacía" }
        val valor = datos[inicio]
        inicio = (inicio + 1) % datos.size
        cantidad--
        return valor
    }
}
