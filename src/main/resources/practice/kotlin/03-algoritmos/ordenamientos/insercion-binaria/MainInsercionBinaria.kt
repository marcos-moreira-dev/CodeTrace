// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-sort.jar"
// Ejecutar: java -jar "$env:TEMP\ct-sort.jar"

/**
 * Cliente de insercion binaria: encontrar hueco en O(log i)
 * NO elimina el movimiento de elementos de un arreglo.
 */
fun main(){
    val a=intArrayOf(5,1,3,3,2)
    InsercionBinaria.ordenar(a)
    check(a.contentEquals(intArrayOf(1,2,3,3,5)))
    InsercionBinaria.ordenar(intArrayOf())
    println("Insercion binaria: "+a.contentToString())
}
/*
 * Salida esperada:
 * Insercion binaria: [1, 2, 3, 3, 5]
 */
