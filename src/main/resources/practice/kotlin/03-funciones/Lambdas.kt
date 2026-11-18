// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\03-funciones\Lambdas.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\codetrace-kotlin.jar"

object Lambdas {
    fun transformar(valor: Int, funcion: (Int) -> Int): Int = funcion(valor)

    @JvmStatic
    fun main(args: Array<String>) {
        val doble: (Int) -> Int = { numero -> numero * 2 }
        println(transformar(7, doble))
    }
}
