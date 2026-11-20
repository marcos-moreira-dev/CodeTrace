// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\03-algoritmos\insercion\Insercion.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

object Insercion {
    fun ordenar(numeros: IntArray) {
        for (i in 1 until numeros.size) {
            val actual = numeros[i]
            var j = i - 1
            while (j >= 0 && numeros[j] > actual) {
                numeros[j + 1] = numeros[j]
                j--
            }
            numeros[j + 1] = actual
        }
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\03-algoritmos\insercion\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 * Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
 * Salida esperada:
 *   (sin salida de consola; la comprobación termina correctamente)
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
