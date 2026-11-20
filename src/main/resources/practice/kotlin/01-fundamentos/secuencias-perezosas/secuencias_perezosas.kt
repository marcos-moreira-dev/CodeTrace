// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar (PowerShell): kotlinc -jvm-target 21 '.\01-fundamentos\secuencias-perezosas\secuencias_perezosas.kt' -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar"

/**
 * Cliente de Sequence: transformación bajo demanda en Kotlin.
 *
 * filter/map sobre listas producen resultados intermedios. Una
 * Sequence difiere operaciones y puede terminar antes al usar take.
 * No implica concurrencia, y su beneficio depende de la entrada.
 */
fun main() {
    val cuadrados = (1..100).asSequence()
        .filter { it % 2 == 0 }
        .map { it * it }
        .take(5)
        .toList()
    println(cuadrados)
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\01-fundamentos\secuencias-perezosas\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   [4, 16, 36, 64, 100]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
