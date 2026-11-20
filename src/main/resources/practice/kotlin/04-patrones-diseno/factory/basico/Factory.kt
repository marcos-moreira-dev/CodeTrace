// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\04-patrones-diseno\Factory.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

interface Mensaje {
    fun enviar(): String
}

class Correo(private val contenido: String) : Mensaje {
    override fun enviar(): String = "Correo: $contenido"
}

object Factory {
    fun crear(tipo: String, contenido: String): Mensaje =
        when (tipo) {
            "correo" -> Correo(contenido)
            else -> throw IllegalArgumentException("Tipo desconocido")
        }
}
