// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Cliente Linked List doble y casos límite de extremos.
 *
 * El enlace anterior permite recorrer hacia atrás y quitar
 * el último nodo en O(1), a cambio de más memoria por nodo.
 * Comparar con la lista simple, que carece de enlace previo.
 */
fun main(){
    val l=ListaDoble<String>()
    listOf("A","B","C").forEach(l::agregarFinal)
    check(l.adelante()==listOf("A","B","C"))
    check(l.atras()==listOf("C","B","A"))
    check(l.quitarFinal()=="C"&&l.cantidad==2)
    println(l.adelante())
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\linked-list\doble\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   [A, B]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
