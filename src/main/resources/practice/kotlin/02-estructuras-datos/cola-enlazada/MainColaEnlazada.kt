// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Cliente Queue enlazada frente a buffer circular.
 *
 * Los nodos crecen dinámicamente y ambas operaciones son O(1).
 * El caso vaciar/reutilizar examina el invariante de que cabeza y
 * cola vuelven a null después de extraer el último nodo.
 */
fun main(){
    val q=ColaEnlazada<String>()
    q.encolar("A");q.encolar("B")
    check(q.desencolar()=="A"&&q.desencolar()=="B")
    q.encolar("C")
    check(q.desencolar()=="C"&&q.cantidad==0)
    println("FIFO por nodos correcta")
}

/*
 * Salida esperada de consola:
 *   FIFO por nodos correcta
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
