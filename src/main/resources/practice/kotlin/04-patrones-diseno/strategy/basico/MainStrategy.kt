// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin-unit.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin-unit.jar"
// El cliente Main... es el único punto de entrada en cada unidad.

/**
 * Cliente Strategy: cambiar un algoritmo sin reescribir el contexto.
 *
 * Kotlin tiene fun interface: una sola función abstracta puede
 * implementarse con una lambda. Una política con estado complejo
 * justificaría una clase propia. Aquí contrastamos ambas decisiones
 * de descuento sobre el mismo precio e invariante de total no negativo.
 */
fun main() {
    val normal = Descuento { it }
    val fidelidad = Descuento { it * 0.90 }
    check(Strategy.calcular(100.0, fidelidad) == 90.0)
    println("Total normal: " + Strategy.calcular(100.0, normal))
    println("Total fidelidad: " + Strategy.calcular(100.0, fidelidad))
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\strategy\basico\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   Total normal: 100.0
 *   Total fidelidad: 90.0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
