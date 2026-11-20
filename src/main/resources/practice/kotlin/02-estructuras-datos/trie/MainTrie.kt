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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\trie\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   Trie correcto
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
