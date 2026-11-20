// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\01-sintaxis\Tipos.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\codetrace-kotlin.jar"

object Tipos {
    @JvmStatic
    fun main(args: Array<String>) {
        val unidades: Int = 5
        val precio: Double = 12.50
        val disponible: Boolean = true
        val producto: String = "Cuaderno"
        println("${producto}: ${unidades * precio}, disponible=${disponible}")
    }
}
