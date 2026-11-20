// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar desde COPIA de la carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-patron.jar"
// Ejecutar: java -jar "$env:TEMP\ct-patron.jar" desde Main...

/**
 * Composite: hojas y grupos implementan el mismo contrato Elemento.
 * El cálculo de tamaño visita n nodos, O(n). Es un árbol en memoria
 * y no una consulta al sistema de archivos.
 */
sealed interface Elemento{fun bytes():Int}
data class Archivo(val nombre:String,val tam:Int):Elemento{
    init{require(tam>=0)}
    override fun bytes()=tam
}
class Carpeta(val nombre:String):Elemento{
    private val hijos=mutableListOf<Elemento>()
    fun agregar(e:Elemento){require(e!==this);hijos.add(e)}
    override fun bytes()=hijos.sumOf(Elemento::bytes)
}
