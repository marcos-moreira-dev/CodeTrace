// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Cliente Deque: una misma secuencia permite FIFO y LIFO.
 *
 * En Kotlin ArrayDeque es una alternativa idiomática; en esta
 * carpeta el objetivo es el contrato, no la memoria de nodos.
 */
fun main(){
    val d=DequeCurso<String>()
    d.insertarPrimero("B");d.insertarPrimero("A");d.insertarUltimo("C")
    check(d.quitarPrimero()=="A"&&d.quitarUltimo()=="C"&&d.quitarUltimo()=="B")
    println("Deque verificado")
}
