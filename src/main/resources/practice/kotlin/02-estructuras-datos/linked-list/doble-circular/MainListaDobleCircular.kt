// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Cliente Linked List doble circular.
 *
 * Combina recorridos bidireccionales y conexiones en anillo.
 * Ningún extremo termina en null; se limita el recorrido
 * explícitamente a la cantidad de elementos presentes.
 */
fun main(){
    val l=ListaDobleCircular<Int>()
    listOf(2,3,5).forEach(l::agregar)
    check(l.valores()==listOf(2,3,5))
    check(l.valores(true)==listOf(5,3,2))
    println(l.valores())
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\linked-list\doble-circular\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   [2, 3, 5]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
