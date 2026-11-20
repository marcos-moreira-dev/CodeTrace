// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/** Lista doble circular: head.prev=tail y tail.next=head. */
class ListaDobleCircular<T>{
    private class Nodo<E>(val valor:E){
        var previo:Nodo<E>?=null
        var siguiente:Nodo<E>?=null
    }
    private var ultimo:Nodo<T>?=null
    private var cantidad=0
    fun agregar(valor:T){
        val n=Nodo(valor)
        if(ultimo==null){n.previo=n;n.siguiente=n}
        else{
            val primero=ultimo!!.siguiente!!
            n.previo=ultimo;n.siguiente=primero
            ultimo!!.siguiente=n;primero.previo=n
        }
        ultimo=n;cantidad++
    }
    fun valores(inverso:Boolean=false):List<T>{
        val r=mutableListOf<T>()
        var n=if(inverso)ultimo else ultimo?.siguiente
        repeat(cantidad){
            r.add(n!!.valor)
            n=if(inverso)n!!.previo else n!!.siguiente
        }
        return r
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\linked-list\doble-circular\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: ListaDobleCircular.kt es un componente; la salida corresponde al cliente MainListaDobleCircular.kt.
 * Salida esperada:
 *   [2, 3, 5]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
