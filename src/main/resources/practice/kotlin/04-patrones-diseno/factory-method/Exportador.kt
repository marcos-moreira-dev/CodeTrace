// CodeTrace | Referencia: Kotlin 2.2, Factory Method
// Compilar: kotlinc 04-patrones-diseno/factory-method/*.kt -include-runtime -d factory.jar
package codetrace.factorymethod
interface Exportador {
    fun exportar(contenido: String): String
}
class ExportadorTexto : Exportador {
    override fun exportar(contenido: String) = "TEXTO: $contenido"
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\factory-method\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: Exportador.kt es un componente; la salida corresponde al cliente MainFactoryMethod.kt.
 * Salida esperada:
 *   TEXTO: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
