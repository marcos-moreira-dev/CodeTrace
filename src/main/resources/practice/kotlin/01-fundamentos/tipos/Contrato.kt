// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\01-fundamentos/tipos\Contrato.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

interface Contrato {
    fun calcular(subtotal: Double): Double

    fun aplica(subtotal: Double): Boolean = subtotal > 0
}

class Impuesto(private val tasa: Double) : Contrato {
    override fun calcular(subtotal: Double): Double = subtotal * tasa
}
