// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar (PowerShell): kotlinc -jvm-target 21 '.\04-patrones-diseno\decorator_notificaciones.kt' -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar"

/**
 * Cliente Decorator mediante composición y delegación.
 *
 * Una notificación se envuelve para añadir una etiqueta, sin
 * modificar el componente original. El mismo contrato permite
 * encadenar otras capas según el dominio del programa.
 */
fun interface Notificador { fun enviar(texto: String) }
class Consola : Notificador {
    override fun enviar(texto: String) = println("Mensaje: $texto")
}
class Prefijo(private val interno: Notificador, private val etiqueta: String) :
    Notificador {
    override fun enviar(texto: String) = interno.enviar(etiqueta + texto)
}
fun main() {
    val emisor: Notificador = Prefijo(Consola(), "[INFO] ")
    emisor.enviar("Servicio listo")
}
