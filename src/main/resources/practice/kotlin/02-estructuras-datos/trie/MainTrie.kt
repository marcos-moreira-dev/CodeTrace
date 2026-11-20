// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Cliente Trie: "cas" es prefijo de "casa" y "casco", pero no
 * necesariamente una palabra. La representación Map<Char,Nodo>
 * intercambia espacio por compatibilidad con distintos alfabetos.
 */
fun main(){
    val t=Trie()
    t.insertar("casa");t.insertar("casco")
    check(t.prefijo("cas")&&!t.contiene("cas"))
    check(t.contiene("casa")&&!t.contiene("cama"))
    println("Trie correcto")
}
