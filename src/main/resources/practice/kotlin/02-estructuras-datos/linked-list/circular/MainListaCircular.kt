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
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\linked-list\circular\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   [Luis, Mia, Ana]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
