// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar desde esta carpeta (PowerShell): kotlinc -jvm-target 21 Factory.kt MainFactory.kt -include-runtime -d "$env:TEMP\factory.jar"
// Ejecutar: java -jar "$env:TEMP\factory.jar"

/**
 * Cliente de fábrica simple idiomática de Kotlin.
 *
 * No se introduce una subclase creadora por obligación: Factory es
 * un object que encapsula la construcción del tipo concreto Correo.
 * Este cliente solo conoce Mensaje y su operación enviar().
 */
fun main() {
    val mensaje: Mensaje = Factory.crear("correo", "Ejemplo de Kotlin")
    println(mensaje.enviar())
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\factory\basico\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   Correo: Ejemplo de Kotlin
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
