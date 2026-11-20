// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar desde COPIA de la carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-patron.jar"
// Ejecutar: java -jar "$env:TEMP\ct-patron.jar" desde Main...

/**
 * Builder: construcción por etapas con validación.
 * La data class es inmutable respecto de sus propiedades val.
 * Los argumentos nombrados Kotlin podrían reemplazar un Builder
 * si las opciones fueran poco numerosas y la validación trivial.
 */
data class Solicitud(val ruta:String,val metodo:String,val cabeceras:List<String>)
class SolicitudBuilder{
    private var ruta=""
    private var metodo="GET"
    private val cabeceras=mutableListOf<String>()
    fun ruta(valor:String)=apply{ruta=valor}
    fun metodo(valor:String)=apply{metodo=valor}
    fun cabecera(valor:String)=apply{cabeceras.add(valor)}
    fun construir():Solicitud{
        require(ruta.startsWith("/")&&metodo.isNotBlank())
        return Solicitud(ruta,metodo,cabeceras.toList())
    }
}
