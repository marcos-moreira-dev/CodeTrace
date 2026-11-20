// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar/Comprobar: kotlinc -jvm-target 21 01-fundamentos/nulos-y-sealed/nulos_y_sealed.kt -include-runtime -d app.jar
// Ejecutar: java -jar app.jar

/**
 * Cliente de nulabilidad y sealed interfaces.
 * Kotlin distingue String de String?, por lo que obliga a resolver
 * el caso null explícitamente. Una sealed interface representa un
 * conjunto conocido de estados, tratado exhaustivamente por when.
 */
sealed interface Respuesta {
    data class Bien(val valor: Int) : Respuesta
    data class Mal(val motivo: String) : Respuesta
}
fun describir(r: Respuesta): String = when (r) {
    is Respuesta.Bien -> "Valor: " + r.valor
    is Respuesta.Mal -> "Error: " + r.motivo
}
fun main() {
    val etiqueta: String? = null
    println(etiqueta?.uppercase() ?: "sin etiqueta")
    println(describir(Respuesta.Bien(21)))
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\01-fundamentos\nulos-y-sealed\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   sin etiqueta
 *   Valor: 21
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
