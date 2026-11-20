// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en copia desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-unidad.jar"
// Ejecutar: java -jar "$env:TEMP\ct-unidad.jar"
// Contrato: una unidad conserva cliente Main... y clases relacionadas.

/**
 * Error propio del dominio: aquí es una RuntimeException.
 *
 * Kotlin NO obliga a declarar/capturar checked exceptions como Java.
 * Un contrato de error puede expresarse con throw, nullable, Result
 * o sealed class según la política del dominio.
 */
class StockInsuficiente(val solicitado: Int, val disponible: Int) :
    IllegalStateException("Pedido=$solicitado, disponible=$disponible")

class Inventario(private var stock: Int) {
    init { require(stock >= 0) }

    fun despachar(cantidad: Int) {
        require(cantidad > 0)
        if (cantidad > stock) throw StockInsuficiente(cantidad, stock)
        stock -= cantidad
    }

    fun disponible(): Int = stock
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\01-fundamentos\excepciones-personalizadas\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: Inventario.kt es un componente; la salida corresponde al cliente MainExcepciones.kt.
 * Salida esperada:
 *   Pedido=8, disponible=3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
