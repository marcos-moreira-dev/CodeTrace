// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar (PowerShell): kotlinc -jvm-target 21 '.\03-algoritmos\mochila-01\mochila_01.kt' -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar"

/**
 * Cliente de mochila 0/1 por programación dinámica.
 *
 * Cada artículo se usa a lo sumo una vez. Recorrer capacidades
 * en orden descendente evita repetir artículos. Tiempo O(n*C),
 * espacio O(C), seudopolinómico respecto a C.
 */
fun mochila(pesos: IntArray, valores: IntArray, capacidad: Int): Int {
    require(capacidad >= 0 && pesos.size == valores.size)
    val dp = IntArray(capacidad + 1)
    for (i in pesos.indices) {
        require(pesos[i] > 0 && valores[i] >= 0)
        for (c in capacidad downTo pesos[i])
            dp[c] = maxOf(dp[c], dp[c - pesos[i]] + valores[i])
    }
    return dp[capacidad]
}
fun main() = println(mochila(intArrayOf(2, 3, 4, 5),
    intArrayOf(3, 4, 5, 8), 8))
