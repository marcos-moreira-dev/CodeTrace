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
