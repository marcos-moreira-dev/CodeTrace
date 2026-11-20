// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar desde COPIA de la carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-patron.jar"
// Ejecutar: java -jar "$env:TEMP\ct-patron.jar" desde Main...

/**
 * Cliente Abstract Factory: intercambiar la familia ENTERA.
 *
 * A diferencia de Factory Method, la fábrica produce varios
 * productos relacionados. fun interface y lambda son productos
 * sin estado adecuados en una demostración de consola.
 */
private fun pantalla(f:Fabrica)=f.boton().pintar()+" / "+f.dialogo().pintar()
fun main(){
    val a=pantalla(Claro());val b=pantalla(Oscuro())
    check(a!=b&&"clara" in a&&"oscura" in b)
    println(a);println(b)
}

/*
 * Salida esperada de consola (dos líneas):
 *   [ OK ] / Ventana clara
 *   < OK > / Ventana oscura
 * Una fábrica cambia ambos productos de la familia.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\abstract-factory\tema-consola\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   [ OK ] / Ventana clara
 *   < OK > / Ventana oscura
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
