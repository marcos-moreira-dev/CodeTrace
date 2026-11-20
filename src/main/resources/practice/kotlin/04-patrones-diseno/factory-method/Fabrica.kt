// CodeTrace | Referencia: Kotlin 2.2, Factory Method
// Compilar: kotlinc 04-patrones-diseno/factory-method/*.kt -include-runtime -d factory.jar
package codetrace.factorymethod
abstract class Fabrica {
    protected abstract fun crear(): Exportador
    fun preparar(contenido: String): String = crear().exportar(contenido)
}
class FabricaTexto : Fabrica() {
    override fun crear(): Exportador = ExportadorTexto()
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\factory-method\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: Fabrica.kt es un componente; la salida corresponde al cliente MainFactoryMethod.kt.
 * Salida esperada:
 *   TEXTO: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
