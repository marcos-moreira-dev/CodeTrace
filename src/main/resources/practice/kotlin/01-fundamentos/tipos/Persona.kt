// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\02-tipos\Persona.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

data class Persona(val nombre: String, val edad: Int) {
    init {
        require(edad >= 0) { "Edad inválida" }
    }

    fun esAdulta(): Boolean = edad >= 18
}
