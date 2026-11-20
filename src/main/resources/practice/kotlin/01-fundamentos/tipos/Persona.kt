// CodeTrace | Kotlin 2.2 | JVM 21 | Java JDK 21.
// Desde PowerShell, en una copia de esta biblioteca:
// Compilar: kotlinc -jvm-target 21 '.\01-fundamentos/tipos\Persona.kt' -include-runtime -d "$env:TEMP\codetrace-kotlin.jar"
// Este archivo declara tipos; se compila, pero no tiene punto de entrada main.

data class Persona(val nombre: String, val edad: Int) {
    init {
        require(edad >= 0) { "Edad inválida" }
    }

    fun esAdulta(): Boolean = edad >= 18
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\01-fundamentos\tipos\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 * Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
 * Salida esperada:
 *   (sin salida de consola; la comprobación termina correctamente)
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
