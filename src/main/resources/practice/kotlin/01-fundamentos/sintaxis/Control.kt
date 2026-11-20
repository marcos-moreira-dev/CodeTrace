// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\01-fundamentos/sintaxis\Control.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\codetrace-kotlin.jar"

object Control {
    fun sumarPares(limite: Int): Int {
        var total = 0
        for (i in 0..limite) {
            if (i % 2 == 0) total += i
        }
        return total
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(sumarPares(8))
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\01-fundamentos\sintaxis\Control.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   20
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
