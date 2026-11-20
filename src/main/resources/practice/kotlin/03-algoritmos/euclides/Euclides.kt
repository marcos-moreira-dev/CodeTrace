// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\03-algoritmos\Euclides.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

object Euclides {
    fun mcd(a: Int, b: Int): Int {
        require(a >= 0 && b >= 0) { "Usa enteros no negativos" }
        var x = a
        var y = b
        while (y != 0) {
            val resto = x % y
            x = y
            y = resto
        }
        return x
    }
}
