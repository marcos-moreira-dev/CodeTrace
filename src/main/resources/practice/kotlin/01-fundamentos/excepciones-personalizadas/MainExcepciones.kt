// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en copia desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-unidad.jar"
// Ejecutar: java -jar "$env:TEMP\ct-unidad.jar"
// Contrato: una unidad conserva cliente Main... y clases relacionadas.

/**
 * Cliente: excepción propia en Kotlin y mantenimiento de invariantes.
 *
 * Una operación fallida no debe mutar stock. Separamos Inventario
 * (dominio), StockInsuficiente (tipo de rechazo) y este cliente
 * para ver la diferencia con las excepciones checked de Java.
 */
fun main() {
    val inventario = Inventario(5)
    inventario.despachar(2)
    try {
        inventario.despachar(8)
        error("El pedido debía fallar")
    } catch (e: StockInsuficiente) {
        check(e.disponible == 3)
        println(e.message)
    }
    check(inventario.disponible() == 3)
}

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
