// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en copia desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-unidad.jar"
// Ejecutar: java -jar "$env:TEMP\ct-unidad.jar"
// Contrato: una unidad conserva cliente Main... y clases relacionadas.

/**
 * Cliente: red dirigida con latencias como pesos de aristas.
 *
 * Una red puede contener ciclos y rutas alternativas, por lo que
 * no se reduce a un árbol. Aquí se construye el modelo; los
 * algoritmos de recorridos y caminos mínimos se enseñan en
 * 03-algoritmos. La ausencia de arco inverso es deliberada.
 */
fun main() {
    val red = Grafo()
    red.conectar("GYE", "UIO", 12.0)
    red.conectar("GYE", "CUE", 18.0)
    red.conectar("UIO", "CUE", 8.0)
    check(red.vecinos("GYE").size == 2)
    check(red.vecinos("CUE").isEmpty())
    println("Desde Guayaquil: " + red.vecinos("GYE"))
}

/*
 * Salida esperada de consola:
 *   Desde Guayaquil: 
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
