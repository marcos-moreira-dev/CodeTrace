// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\04-patrones-diseno\Strategy.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\codetrace-kotlin.jar"

fun interface Descuento {
    fun aplicar(total: Double): Double
}

object Strategy {
    fun calcular(total: Double, estrategia: Descuento): Double =
        estrategia.aplicar(total)

    @JvmStatic
    fun main(args: Array<String>) {
        val diezPorCiento = Descuento { subtotal -> subtotal * 0.9 }
        println(calcular(100.0, diezPorCiento))
    }
}
