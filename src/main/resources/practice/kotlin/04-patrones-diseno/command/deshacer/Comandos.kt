// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar desde COPIA de la carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-patron.jar"
// Ejecutar: java -jar "$env:TEMP\ct-patron.jar" desde Main...

/**
 * Command: objeto con ejecutar/deshacer; Editor es el receptor.
 * El historial de comandos es responsabilidad del invocador.
 */
interface Comando{fun ejecutar();fun deshacer()}
class Editor{
    var texto:String=""
        private set
    fun agregar(x:String){texto+=x}
    fun restaurar(x:String){texto=x}
}
class AgregarTexto(private val editor:Editor,private val parte:String):Comando{
    private var anterior:String?=null
    override fun ejecutar(){
        check(anterior==null)
        anterior=editor.texto;editor.agregar(parte)
    }
    override fun deshacer(){
        val valor=anterior?:error("Comando no ejecutado")
        editor.restaurar(valor);anterior=null
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\command\deshacer\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: Comandos.kt es un componente; la salida corresponde al cliente MainCommand.kt.
 * Salida esperada:
 *   Hola
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
