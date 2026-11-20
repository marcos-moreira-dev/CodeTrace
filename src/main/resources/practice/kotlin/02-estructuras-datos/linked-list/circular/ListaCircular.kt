// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/** Lista circular simple: tail.next vuelve al primer nodo. */
class ListaCircular<T>{
    private class Nodo<E>(val valor:E){var siguiente:Nodo<E>?=null}
    private var ultimo:Nodo<T>?=null
    private var cantidad=0
    fun agregar(valor:T){
        val n=Nodo(valor)
        n.siguiente=ultimo?.siguiente?:n
        ultimo?.siguiente=n
        ultimo=n;cantidad++
    }
    fun rotar(){ultimo=ultimo?.siguiente}
    fun valores():List<T>{
        val r=mutableListOf<T>()
        var n=ultimo?.siguiente
        repeat(cantidad){r.add(n!!.valor);n=n!!.siguiente}
        return r
    }
}
