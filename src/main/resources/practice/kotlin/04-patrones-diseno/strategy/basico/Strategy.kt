// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin-unit.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin-unit.jar"
// El cliente Main... es el único punto de entrada en cada unidad.

/** Contrato intercambiable: fun interface permite implementar con lambda. */
fun interface Descuento { fun aplicar(total: Double): Double }

/** Contexto que delega su cálculo a una estrategia externa. */
object Strategy {
    fun calcular(total: Double, estrategia: Descuento): Double {
        require(total >= 0)
        return estrategia.aplicar(total)
    }
}
