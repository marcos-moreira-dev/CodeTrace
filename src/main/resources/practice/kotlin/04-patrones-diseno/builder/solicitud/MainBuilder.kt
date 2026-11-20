// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar desde COPIA de la carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-patron.jar"
// Ejecutar: java -jar "$env:TEMP\ct-patron.jar" desde Main...

/**
 * Cliente Builder: separar pasos de configuración del producto válido.
 *
 * En Kotlin no conviene copiar toda clase Java: apply, val y data class
 * permiten construir un objeto de forma idiomática. Se demuestra el
 * valor añadido de validar antes de entregar una solicitud inmutable.
 */
fun main(){
    val s=SolicitudBuilder().ruta("/facturas").metodo("POST")
        .cabecera("Content-Type: application/json").construir()
    check(s.ruta=="/facturas"&&s.cabeceras.size==1)
    println("${s.metodo} ${s.ruta} (${s.cabeceras.size} cabecera)")
}

/*
 * Salida esperada de consola:
 *   POST /facturas (1 cabecera)
 * La validación del Builder entrega un producto con una cabecera.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\builder\solicitud\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   POST /facturas (1 cabecera)
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
