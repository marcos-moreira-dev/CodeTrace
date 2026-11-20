// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\01-fundamentos/tipos\Contrato.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

interface Contrato {
    fun calcular(subtotal: Double): Double

    fun aplica(subtotal: Double): Boolean = subtotal > 0
}

class Impuesto(private val tasa: Double) : Contrato {
    override fun calcular(subtotal: Double): Double = subtotal * tasa
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\01-fundamentos\tipos\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 * Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
 * Salida esperada:
 *   (sin salida de consola; la comprobación termina correctamente)
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
