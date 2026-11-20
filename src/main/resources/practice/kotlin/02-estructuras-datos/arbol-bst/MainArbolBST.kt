// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en copia desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-unidad.jar"
// Ejecutar: java -jar "$env:TEMP\ct-unidad.jar"
// Contrato: una unidad conserva cliente Main... y clases relacionadas.

/**
 * Cliente: inserción y recorrido inorden de un BST genérico.
 *
 * El comparador separa el dominio T de la política de orden. Cuando
 * la estructura se desequilibra, la altura h llega a n; por eso
 * AVL garantiza O(log n) donde un BST ordinario no puede hacerlo.
 */
fun main() {
    val bst = ArbolBST<Int>(compareBy { it })
    listOf(8, 3, 10, 1, 6, 4, 7).forEach(bst::insertar)
    check(bst.inorden() == listOf(1, 3, 4, 6, 7, 8, 10))
    println("Inorden: " + bst.inorden())
}

/*
 * Salida esperada de consola:
 *   Inorden: 
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
