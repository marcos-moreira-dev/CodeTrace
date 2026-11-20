// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Cliente grafos ponderados, con aristas dirigidas y costos.
 *
 * El grafo describe datos, no el algoritmo de rutas. Dijkstra
 * exige costos no negativos; este modelo permite otros pesos
 * para contrastar algoritmos con sus precondiciones.
 */
fun main(){
    val g=GrafoPonderado()
    g.conectar("GYE","UIO",12.0)
    g.conectar("GYE","CUE",18.0)
    g.conectar("UIO","CUE",8.0)
    check(g.vecinos("GYE").size==2&&g.vecinos("CUE").isEmpty())
    println(g.vecinos("GYE"))
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\grafos\ponderados\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   [Arista(destino=UIO, peso=12.0), Arista(destino=CUE, peso=18.0)]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
