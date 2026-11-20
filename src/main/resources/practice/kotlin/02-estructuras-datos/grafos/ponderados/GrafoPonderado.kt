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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\grafos\ponderados\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: GrafoPonderado.kt es un componente; la salida corresponde al cliente MainGrafoPonderado.kt.
 * Salida esperada:
 *   [Arista(destino=UIO, peso=12.0), Arista(destino=CUE, peso=18.0)]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
