// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar desde COPIA de la carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-patron.jar"
// Ejecutar: java -jar "$env:TEMP\ct-patron.jar" desde Main...

/**
 * Cliente Composite: consumir archivo y carpeta uniformemente.
 *
 * Una carpeta contiene otras hojas o carpetas sin preguntar qué
 * clase concreta son al sumar sus tamaños. La prevención de ciclos
 * indirectos se omite: el dominio aquí es un árbol acíclico.
 */
fun main(){
    val raiz=Carpeta("proyecto")
    val src=Carpeta("src")
    src.agregar(Archivo("Main.kt",120));src.agregar(Archivo("App.kt",80))
    raiz.agregar(src);raiz.agregar(Archivo("README.md",20))
    val e:Elemento=raiz
    check(e.bytes()==220)
    println(e.bytes())
}

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
