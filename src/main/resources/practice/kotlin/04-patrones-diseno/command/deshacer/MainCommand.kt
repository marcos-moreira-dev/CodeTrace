// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar desde COPIA de la carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-patron.jar"
// Ejecutar: java -jar "$env:TEMP\ct-patron.jar" desde Main...

/**
 * Cliente Command con historial LIFO y Undo.
 *
 * La operación se expresa como un objeto, no simplemente una llamada
 * a Editor.agregar. Una vez ejecutada puede entrar en una pila y
 * recuperar el estado anterior, desacoplando la UI de la edición.
 */
fun main(){
    val e=Editor()
    val historial=ArrayDeque<Comando>()
    for(parte in listOf("Hola"," mundo")){
        val c=AgregarTexto(e,parte);c.ejecutar();historial.addLast(c)
    }
    check(e.texto=="Hola mundo")
    historial.removeLast().deshacer()
    check(e.texto=="Hola")
    println(e.texto)
}

/*
 * Salida esperada de consola:
 *   Hola
 * Undo revierte solamente el comando más reciente (« mundo»).
 */
