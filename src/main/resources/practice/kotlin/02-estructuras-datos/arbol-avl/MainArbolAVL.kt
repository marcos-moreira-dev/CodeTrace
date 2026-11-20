// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Cliente AVL con la entrada adversa 1..15.
 *
 * Un BST sin balanceo degenera a cadena en esa entrada; el AVL
 * usa rotaciones para lograr altura logarítmica. Se comprueba
 * el inorden y la altura real almacenada en cada nodo.
 */
fun main(){
    val arbol=ArbolAVL()
    (1..15).forEach(arbol::insertar)
    check(arbol.inorden()==(1..15).toList())
    check(arbol.validar()<=5)
    println("Altura AVL: "+arbol.validar())
}
