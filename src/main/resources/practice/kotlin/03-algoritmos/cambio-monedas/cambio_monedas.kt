// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar/Comprobar: kotlinc -jvm-target 21 ARCHIVO.kt -include-runtime -d app.jar
// Ejecutar: java -jar app.jar

/**
 * Cliente de programación dinámica con Kotlin.
 * El problema del cambio mínimo reusa estados para montos menores:
 * con denominaciones 1,3,4 y monto 6 obtiene dos monedas (3+3).
 * Tiempo O(m*k), memoria O(m); no prometer que greedy sea óptimo.
 */
fun monedasMinimas(monedas: List<Int>, monto: Int): Int {
    require(monto >= 0 && monedas.isNotEmpty() && monedas.all { it > 0 })
    val infinito = monto + 1
    val dp = IntArray(monto + 1) { infinito }
    dp[0] = 0
    for (m in 1..monto)
        for (x in monedas)
            if (x <= m) dp[m] = minOf(dp[m], dp[m - x] + 1)
    return if (dp[monto] == infinito) -1 else dp[monto]
}
fun main() { println(monedasMinimas(listOf(1, 3, 4), 6)) }
