// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Cliente heap máximo: no es una pila, ni cola FIFO, ni BST.
 *
 * El elemento máximo vive en la raíz, pero el arreglo interno
 * no está completamente ordenado. Extraer reorganiza los nodos
 * para restaurar padre >= hijos.
 */
fun main(){
    val h=HeapMaximo()
    listOf(4,1,9,7,2).forEach(h::insertar)
    check(List(5){h.extraer()}==listOf(9,7,4,2,1))
    println("Max heap correcto")
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\heap-maximo\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   Max heap correcto
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
