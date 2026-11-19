// CodeTrace | Referencia: Kotlin 2.2, Factory Method
// Compilar: kotlinc 07-proyectos/factory-method/*.kt -include-runtime -d factory.jar
package codetrace.factorymethod
interface Exportador {
    fun exportar(contenido: String): String
}
class ExportadorTexto : Exportador {
    override fun exportar(contenido: String) = "TEXTO: $contenido"
}
