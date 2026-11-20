// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar desde COPIA de la carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-patron.jar"
// Ejecutar: java -jar "$env:TEMP\ct-patron.jar" desde Main...

/** Abstract Factory: una familia devuelve Boton y Dialogo coherentes. */
fun interface Boton{fun pintar():String}
fun interface Dialogo{fun pintar():String}
interface Fabrica{fun boton():Boton;fun dialogo():Dialogo}
class Claro:Fabrica{
    override fun boton()=Boton{"[ OK ]"}
    override fun dialogo()=Dialogo{"Ventana clara"}
}
class Oscuro:Fabrica{
    override fun boton()=Boton{"< OK >"}
    override fun dialogo()=Dialogo{"Ventana oscura"}
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\abstract-factory\tema-consola\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: Familias.kt es un componente; la salida corresponde al cliente MainAbstractFactory.kt.
 * Salida esperada:
 *   [ OK ] / Ventana clara
 *   < OK > / Ventana oscura
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
