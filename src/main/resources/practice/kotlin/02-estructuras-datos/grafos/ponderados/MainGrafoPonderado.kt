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
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
