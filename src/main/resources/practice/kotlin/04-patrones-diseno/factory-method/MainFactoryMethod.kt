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
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\factory-method\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   TEXTO: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
