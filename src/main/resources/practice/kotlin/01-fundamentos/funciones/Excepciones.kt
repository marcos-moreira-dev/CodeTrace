// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\01-fundamentos/funciones\Excepciones.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\codetrace-kotlin.jar"

object Excepciones {
    fun dividir(a: Int, b: Int): Int {
        require(b != 0) { "Divisor cero" }
        return a / b
    }

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            println(dividir(12, 3))
        } catch (error: IllegalArgumentException) {
            println(error.message)
        }
    }
}
