// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin-unit.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin-unit.jar"
// El cliente Main... es el único punto de entrada en cada unidad.

/**
 * Cliente Decorator: añadir un prefijo sin cambiar el componente original.
 *
 * En vez de una subclase por cada combinación, se envuelve un Notificador
 * con otro Notificador. Los decoradores pueden encadenarse; la delegación
 * preserva la interfaz y permite agregar capacidades gradualmente.
 */
fun main() {
    val notificador: Notificador = Prefijo(Consola(), "[INFO] ")
    notificador.enviar("Pedido despachado")
}

/*
 * Salida esperada de consola:
 *   Sin salida de texto; las pruebas del cliente se cumplen.
 * Verificacion: termina sin excepciones inesperadas.
 */
