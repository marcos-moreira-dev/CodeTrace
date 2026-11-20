// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar desde COPIA de la carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-patron.jar"
// Ejecutar: java -jar "$env:TEMP\ct-patron.jar" desde Main...

/**
 * Cliente Template Method: pasos fijos, formatos intercambiables
 * por subclases. Kotlin final asegura que ejecutar() no se pueda
 * sobrescribir para saltarse su validación inicial.
 */
fun main(){
    val texto:ProcesoReporte=ReporteTexto()
    val csv:ProcesoReporte=ReporteCsv()
    check("Total: 15" in texto.ejecutar("ventas",15))
    check("ventas,15" in csv.ejecutar("ventas",15))
    print(texto.ejecutar("ventas",15)+csv.ejecutar("ventas",15))
}

/*
 * Salida esperada de consola (cuatro líneas):
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 * El método plantilla fija validación y pasos; las subclases aportan el formato.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\template-method\reportes\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
