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
    println(texto.ejecutar("ventas",15)+csv.ejecutar("ventas",15))
}

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
