// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin-unit.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin-unit.jar"
// El cliente Main... es el único punto de entrada en cada unidad.

/**
 * Cliente: lista enlazada genérica, valores conectados por referencias.
 *
 * Insertar en la cabeza cuesta O(1). Una lista enlazada no garantiza
 * acceso O(1) a la posición i, a diferencia de un arreglo. Kotlin
 * utiliza tipos Nodo<T>? para representar ausencia sin un centinela.
 */
fun main() {
    val lista = ListaSimple<String>()
    lista.agregar("Matemática")
    lista.agregar("Estructuras")
    check(lista.cantidad == 2 && lista.obtenerPrimero() == "Estructuras")
    println("Primero: " + lista.obtenerPrimero())
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\linked-list\simple\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   Primero: Estructuras
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
