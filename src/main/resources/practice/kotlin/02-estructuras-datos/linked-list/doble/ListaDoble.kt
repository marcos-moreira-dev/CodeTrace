// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Linked List doble: cada nodo tiene anterior y siguiente.
 * Agregar/quitar al final O(1); recorrido O(n), espacio O(n).
 */
class ListaDoble<T> {
    private class Nodo<E>(val valor:E){
        var anterior:Nodo<E>?=null
        var siguiente:Nodo<E>?=null
    }
    private var primero:Nodo<T>?=null
    private var ultimo:Nodo<T>?=null
    var cantidad=0
        private set
    fun agregarFinal(valor:T){
        val n=Nodo(valor);n.anterior=ultimo
        if(ultimo==null)primero=n else ultimo!!.siguiente=n
        ultimo=n;cantidad++
    }
    fun quitarFinal():T{
        val n=ultimo?:throw NoSuchElementException("Lista vacía")
        ultimo=n.anterior
        if(ultimo==null)primero=null else ultimo!!.siguiente=null
        cantidad--;return n.valor
    }
    fun adelante():List<T>{
        val r=mutableListOf<T>()
        var n=primero
        while(n!=null){r.add(n.valor);n=n.siguiente}
        return r
    }
    fun atras():List<T>{
        val r=mutableListOf<T>()
        var n=ultimo
        while(n!=null){r.add(n.valor);n=n.anterior}
        return r
    }
}
