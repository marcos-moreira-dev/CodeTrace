// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin-unit.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin-unit.jar"
// El cliente Main... es el único punto de entrada en cada unidad.

package codetrace.factorymethod

/**
 * Cliente de Factory Method, diferente de una mera función fábrica.
 *
 * Fabrica declara el método de creación abstracto; la subclase
 * FabricaTexto decide el producto concreto. preparar() reutiliza
 * la misma secuencia sin conocer la clase ExportadorTexto.
 * El cliente escoge la creadora, no implementa el producto.
 */
fun main() {
    val fabrica: Fabrica = FabricaTexto()
    val texto = fabrica.preparar("Factura 001")
    check(texto.isNotBlank())
    println(texto)
}

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
