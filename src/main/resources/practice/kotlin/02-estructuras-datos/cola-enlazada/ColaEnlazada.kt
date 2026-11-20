// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/** Cola FIFO por nodos, sin capacidad fija. Encolar/desencolar O(1). */
class ColaEnlazada<T>{
    private class Nodo<E>(val valor:E){var siguiente:Nodo<E>?=null}
    private var primero:Nodo<T>?=null
    private var ultimo:Nodo<T>?=null
    var cantidad=0
        private set
    fun encolar(valor:T){
        val n=Nodo(valor)
        if(ultimo==null)primero=n else ultimo!!.siguiente=n
        ultimo=n;cantidad++
    }
    fun desencolar():T{
        val n=primero?:throw NoSuchElementException("Cola vacía")
        primero=n.siguiente
        if(primero==null)ultimo=null
        cantidad--;return n.valor
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\cola-enlazada\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: ColaEnlazada.kt es un componente; la salida corresponde al cliente MainColaEnlazada.kt.
 * Salida esperada:
 *   FIFO por nodos correcta
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
