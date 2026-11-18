// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\03-funciones\Callbacks.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
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
