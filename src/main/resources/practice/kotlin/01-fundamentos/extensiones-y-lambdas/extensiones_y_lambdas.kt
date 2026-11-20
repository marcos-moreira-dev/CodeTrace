// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar/Comprobar: kotlinc -jvm-target 21 ARCHIVO.kt -include-runtime -d app.jar
// Ejecutar: java -jar app.jar

/**
 * Cliente de funciones de extensión y colecciones funcionales.
 * Una extensión no cambia el tipo original: ofrece una sintaxis
 * cómoda para un método resuelto estáticamente. filter selecciona
 * valores y map los transforma; la lista de origen se conserva.
 */
fun Int.esPar(): Boolean = this % 2 == 0
fun main() {
    val numeros = listOf(1, 2, 3, 4, 5, 6)
    println(numeros.filter(Int::esPar).map { it * it })
}
