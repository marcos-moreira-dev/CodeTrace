// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en copia desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-unidad.jar"
// Ejecutar: java -jar "$env:TEMP\ct-unidad.jar"
// Contrato: una unidad conserva cliente Main... y clases relacionadas.

/**
 * Cliente: colisiones, igualdad de claves y actualización en hash.
 *
 * Una capacidad de dos buckets aumenta colisiones en este pequeño
 * ensayo. No se confunde igualdad de hash con igualdad de cadenas.
 * Kotlin representa ausencia de un valor con V? (null safety).
 */
fun main() {
    val tabla = TablaHash<Int>(2)
    tabla.poner("A", 1)
    tabla.poner("B", 2)
    tabla.poner("A", 3)
    check(tabla.obtener("A") == 3 && tabla.obtener("B") == 2)
    check(tabla.obtener("sin-clave") == null)
    println("Tabla hash: A=" + tabla.obtener("A"))
}

/*
 * Salida esperada de consola:
 *   Tabla hash: A=
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
