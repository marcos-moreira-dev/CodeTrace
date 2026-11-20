// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en copia desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-unidad.jar"
// Ejecutar: java -jar "$env:TEMP\ct-unidad.jar"
// Contrato: una unidad conserva cliente Main... y clases relacionadas.

/**
 * Árbol binario de búsqueda genérico con Comparator<T>.
 *
 * Invariante: todo descendiente del subárbol izquierdo es menor
 * que el nodo; todo descendiente del derecho es mayor. El BST
 * NO se autoequilibra: insertar cuesta O(h), peor caso O(n).
 */
class ArbolBST<T>(private val orden: Comparator<T>) {
    private class Nodo<T>(val dato: T) {
        var izq: Nodo<T>? = null
        var der: Nodo<T>? = null
    }
    private var raiz: Nodo<T>? = null

    fun insertar(dato: T) {
        fun agregar(actual: Nodo<T>?, valor: T): Nodo<T> {
            if (actual == null) return Nodo(valor)
            val c = orden.compare(valor, actual.dato)
            if (c < 0) actual.izq = agregar(actual.izq, valor)
            else if (c > 0) actual.der = agregar(actual.der, valor)
            return actual
        }
        raiz = agregar(raiz, dato)
    }

    fun inorden(): List<T> {
        val salida = mutableListOf<T>()
        fun visitar(n: Nodo<T>?) {
            if (n == null) return
            visitar(n.izq)
            salida.add(n.dato)
            visitar(n.der)
        }
        visitar(raiz)
        return salida
    }
}
