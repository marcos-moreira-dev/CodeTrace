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
 * Salida esperada de consola:
 *   Total normal: 
 *   Total fidelidad: 
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
