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
