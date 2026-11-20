// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\04-patrones-diseno\factory\basico\Factory.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

/**
 * Fábrica simple Kotlin: separa la decisión de construcción del cliente.
 *
 * <p>El object Factory actúa como instancia única de fábrica. No exige
 * instanciar una jerarquía de creadores y no es idéntico a Factory Method.
 * Véase el cliente MainFactory.kt en la misma carpeta.
 */
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\factory\basico\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: Factory.kt es un componente; la salida corresponde al cliente MainFactory.kt.
 * Salida esperada:
 *   Correo: Ejemplo de Kotlin
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
