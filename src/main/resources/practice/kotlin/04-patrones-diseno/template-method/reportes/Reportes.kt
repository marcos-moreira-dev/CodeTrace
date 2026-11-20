// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar desde COPIA de la carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-patron.jar"
// Ejecutar: java -jar "$env:TEMP\ct-patron.jar" desde Main...

/**
 * Template Method: un método final fija el flujo y las subclases
 * implementan los pasos variables. Frente a Strategy, el cambio
 * ocurre mediante herencia, no una política externa.
 */
abstract class ProcesoReporte{
    final fun ejecutar(titulo:String,total:Int):String{
        require(titulo.isNotBlank()&&total>=0)
        return encabezado(titulo)+detalle(total)+pie()
    }
    protected abstract fun encabezado(titulo:String):String
    protected abstract fun detalle(total:Int):String
    protected open fun pie()="\n"
}
class ReporteTexto:ProcesoReporte(){
    override fun encabezado(titulo:String)="== $titulo ==\n"
    override fun detalle(total:Int)="Total: $total"
}
class ReporteCsv:ProcesoReporte(){
    override fun encabezado(titulo:String)="titulo,total\n"
    override fun detalle(total:Int)="ventas,$total"
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\template-method\reportes\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: Reportes.kt es un componente; la salida corresponde al cliente MainTemplateMethod.kt.
 * Salida esperada:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
