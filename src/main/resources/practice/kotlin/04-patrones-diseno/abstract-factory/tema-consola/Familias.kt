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
