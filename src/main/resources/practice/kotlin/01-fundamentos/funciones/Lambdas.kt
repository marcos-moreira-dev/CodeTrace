// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\01-fundamentos/funciones\Lambdas.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\codetrace-kotlin.jar"

object Lambdas {
    fun transformar(valor: Int, funcion: (Int) -> Int): Int = funcion(valor)

    @JvmStatic
    fun main(args: Array<String>) {
        val doble: (Int) -> Int = { numero -> numero * 2 }
        println(transformar(7, doble))
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\01-fundamentos\funciones\Lambdas.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   14
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
