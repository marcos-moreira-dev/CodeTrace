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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\linked-list\circular\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: ListaCircular.kt es un componente; la salida corresponde al cliente MainListaCircular.kt.
 * Salida esperada:
 *   [Luis, Mia, Ana]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
