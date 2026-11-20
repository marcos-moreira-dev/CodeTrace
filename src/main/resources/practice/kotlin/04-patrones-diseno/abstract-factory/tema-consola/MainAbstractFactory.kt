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
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
