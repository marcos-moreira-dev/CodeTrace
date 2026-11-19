// CodeTrace | Referencia: Kotlin 2.2, Factory Method
// Compilar: kotlinc 07-proyectos/factory-method/*.kt -include-runtime -d factory.jar
package codetrace.factorymethod
fun main() {
    println(FabricaTexto().preparar("Factura 001"))
}
