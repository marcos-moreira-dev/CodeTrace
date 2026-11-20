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
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\01-fundamentos\excepciones-personalizadas\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   Pedido=8, disponible=3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
