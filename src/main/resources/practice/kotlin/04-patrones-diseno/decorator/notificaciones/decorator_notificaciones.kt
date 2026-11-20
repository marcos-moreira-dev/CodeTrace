// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin-unit.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin-unit.jar"
// El cliente Main... es el único punto de entrada en cada unidad.

/** Contrato para emitir mensajes desde diferentes implementaciones. */
fun interface Notificador { fun enviar(texto: String) }

class Consola : Notificador {
    override fun enviar(texto: String) = println("Mensaje: " + texto)
}

/** Conserva el contrato y delega a otro Notificador. */
class Prefijo(private val interno: Notificador, private val etiqueta: String) : Notificador {
    override fun enviar(texto: String) = interno.enviar(etiqueta + texto)
}
