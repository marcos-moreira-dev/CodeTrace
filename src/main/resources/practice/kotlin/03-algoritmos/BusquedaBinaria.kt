// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\03-algoritmos\BusquedaBinaria.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

object BusquedaBinaria {
    fun buscar(ordenados: IntArray, objetivo: Int): Int {
        var izquierda = 0
        var derecha = ordenados.lastIndex
        while (izquierda <= derecha) {
            val medio = izquierda + (derecha - izquierda) / 2
            when {
                ordenados[medio] == objetivo -> return medio
                ordenados[medio] < objetivo -> izquierda = medio + 1
                else -> derecha = medio - 1
            }
        }
        return -1
    }
}
