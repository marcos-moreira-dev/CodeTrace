// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\01-fundamentos/funciones\Callbacks.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\codetrace-kotlin.jar"

fun interface Receptor {
    fun recibir(mensaje: String)
}

object Callbacks {
    fun notificar(texto: String, receptor: Receptor) = receptor.recibir(texto)

    @JvmStatic
    fun main(args: Array<String>) {
        notificar("Hola", Receptor { println(it) })
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\01-fundamentos\funciones\Callbacks.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   Hola
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
