// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\04-patrones-diseno\observer\basico\Observador.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

fun interface Escucha {
    fun alCambiar(nuevoValor: Int)
}

class Observador {
    private val oyentes = mutableListOf<Escucha>()
    var valor: Int = 0
        private set

    fun suscribir(escucha: Escucha) {
        oyentes.add(escucha)
    }

    fun actualizar(nuevoValor: Int) {
        valor = nuevoValor
        for (escucha in oyentes) escucha.alCambiar(valor)
    }
}
