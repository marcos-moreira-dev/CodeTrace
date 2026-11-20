// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-sort.jar"
// Ejecutar: java -jar "$env:TEMP\ct-sort.jar"

/**
 * Cliente: cinco ordenamientos con la MISMA entrada clonada.
 * Esta prueba funcional no demuestra rendimiento: requiere
 * estudio de invariantes y experimentos controlados. Se prueban
 * ademas arreglos vacios y valores duplicados.
 */
fun main(){
    val metodos=listOf<Pair<String,(IntArray)->Unit>>(
        "Insercion" to Ordenadores::insercion,
        "Burbuja" to Ordenadores::burbuja,
        "Mezcla" to Ordenadores::mezcla,
        "HeapSort" to Ordenadores::heap,
        "QuickSort" to Ordenadores::rapido)
    val esperado=intArrayOf(1,2,3,5,7,8,9)
    for((nombre,fn) in metodos){
        val datos=intArrayOf(9,3,8,1,7,2,5)
        fn(datos);check(datos.contentEquals(esperado))
        println(nombre+": "+datos.contentToString())
        val vacio=intArrayOf();val repetidos=intArrayOf(3,1,3,1)
        fn(vacio);fn(repetidos)
        check(vacio.isEmpty()&&repetidos.contentEquals(intArrayOf(1,1,3,3)))
    }
}
/*
 * Salida esperada, cinco lineas:
 * Insercion: [1, 2, 3, 5, 7, 8, 9]
 * Burbuja: [1, 2, 3, 5, 7, 8, 9]
 * Mezcla: [1, 2, 3, 5, 7, 8, 9]
 * HeapSort: [1, 2, 3, 5, 7, 8, 9]
 * QuickSort: [1, 2, 3, 5, 7, 8, 9]
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\03-algoritmos\ordenamientos\comparativa-cinco\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   Insercion: [1, 2, 3, 5, 7, 8, 9]
 *   Burbuja: [1, 2, 3, 5, 7, 8, 9]
 *   Mezcla: [1, 2, 3, 5, 7, 8, 9]
 *   HeapSort: [1, 2, 3, 5, 7, 8, 9]
 *   QuickSort: [1, 2, 3, 5, 7, 8, 9]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
