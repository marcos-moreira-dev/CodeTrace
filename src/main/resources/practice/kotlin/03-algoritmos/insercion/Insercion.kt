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
