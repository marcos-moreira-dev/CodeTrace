// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar (PowerShell): kotlinc -jvm-target 21 '.\02-estructuras-datos\heap-prioridades\heap_prioridades.kt' -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar"

import java.util.PriorityQueue

/**
 * Cliente de PriorityQueue en Kotlin y la biblioteca Java.
 *
 * La interoperabilidad con JVM permite utilizar una implementación
 * heap eficiente. Ofrecer y extraer cuestan O(log n). Este ejemplo
 * enseña su API, no intenta reimplementar la estructura.
 */
fun main() {
    val prioridades = PriorityQueue<Int>()
    listOf(7, 2, 5, 1, 9).forEach(prioridades::offer)
    while (prioridades.isNotEmpty()) println(prioridades.poll())
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\heap-prioridades\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   1
 *   2
 *   5
 *   7
 *   9
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
