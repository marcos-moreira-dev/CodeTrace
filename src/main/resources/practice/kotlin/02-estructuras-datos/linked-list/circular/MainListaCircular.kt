// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Cliente lista circular para asignación de turnos round-robin.
 *
 * Los nodos no terminan en null. Recorrer mientras el nodo
 * exista produciría bucle infinito: se visita cantidad veces.
 * Rotar cuesta O(1) y recorrer todos los turnos O(n).
 */
fun main(){
    val l=ListaCircular<String>()
    check(l.valores().isEmpty())
    listOf("Ana","Luis","Mia").forEach(l::agregar)
    l.rotar()
    check(l.valores()==listOf("Luis","Mia","Ana"))
    println(l.valores())
}

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
