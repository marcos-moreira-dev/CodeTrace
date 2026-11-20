// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin-unit.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin-unit.jar"
// El cliente Main... es el único punto de entrada en cada unidad.

/**
 * Cliente de cola circular: reutilizar espacio con índice módulo capacidad.
 *
 * Encolar y desencolar tienen costo O(1), no es necesario desplazar datos.
 * El contrato distingue llena y vacía. Se comprueba explícitamente
 * el wrap-around al introducir un cuarto valor tras retirar el primero.
 */
fun main() {
    val cola = ColaCircular(3)
    listOf(10, 20, 30).forEach(cola::encolar)
    check(cola.desencolar() == 10)
    cola.encolar(40)
    check(listOf(cola.desencolar(), cola.desencolar(), cola.desencolar()) ==
        listOf(20, 30, 40))
    println("Cola circular FIFO verificada")
}

/*
 * Salida esperada de consola:
 *   Cola circular FIFO verificada
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
