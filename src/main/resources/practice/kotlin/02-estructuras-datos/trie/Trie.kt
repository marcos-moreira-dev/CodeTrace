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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\trie\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: Trie.kt es un componente; la salida corresponde al cliente MainTrie.kt.
 * Salida esperada:
 *   Trie correcto
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
