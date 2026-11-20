// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/** Trie de prefijos por Char UTF-16; insertar/consulta O(L). */
class Trie{
    private class Nodo{
        val hijos=mutableMapOf<Char,Nodo>()
        var final=false
    }
    private val raiz=Nodo()
    fun insertar(palabra:String){
        var n=raiz
        for(c in palabra)n=n.hijos.getOrPut(c){Nodo()}
        n.final=true
    }
    private fun nodo(palabra:String):Nodo?{
        var n=raiz
        for(c in palabra)n=n.hijos[c]?:return null
        return n
    }
    fun contiene(x:String)=nodo(x)?.final==true
    fun prefijo(x:String)=nodo(x)!=null
}
