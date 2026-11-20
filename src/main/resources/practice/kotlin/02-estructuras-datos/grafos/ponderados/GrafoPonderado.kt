// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/** Representación de grafo dirigido con pesos, memoria O(V+E). */
data class Arista(val destino:String,val peso:Double)
class GrafoPonderado{
    private val ady=mutableMapOf<String,MutableList<Arista>>()
    fun conectar(origen:String,destino:String,peso:Double){
        require(peso.isFinite())
        ady.getOrPut(origen){mutableListOf()}.add(Arista(destino,peso))
        ady.getOrPut(destino){mutableListOf()}
    }
    fun vecinos(x:String):List<Arista> = ady.getValue(x).toList()
}
