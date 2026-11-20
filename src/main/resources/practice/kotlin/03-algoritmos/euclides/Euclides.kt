// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\03-algoritmos\euclides\Euclides.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\03-algoritmos\euclides\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 * Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
 * Salida esperada:
 *   (sin salida de consola; la comprobación termina correctamente)
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
