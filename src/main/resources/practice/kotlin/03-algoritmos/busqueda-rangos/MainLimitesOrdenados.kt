// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-sort.jar"
// Ejecutar: java -jar "$env:TEMP\ct-sort.jar"

/**
 * Cliente Lower/Upper Bound: buscar rango [inicio,fin) de
 * valores repetidos y posicion de insercion de un ausente.
 * Precondicion: datos ya ordenados ascendentemente.
 */
fun main(){
    val a=intArrayOf(1,3,3,3,5,9)
    val lo=LimitesOrdenados.lower(a,3)
    val hi=LimitesOrdenados.upper(a,3)
    val pos=LimitesOrdenados.lower(a,4)
    check(lo==1&&hi==4&&pos==4&&LimitesOrdenados.upper(a,4)==4)
    println("Valor 3: ["+lo+", "+hi+")")
    println("Insertar 4 en indice: "+pos)
}
/*
 * Salida esperada:
 * Valor 3: [1, 4)
 * Insertar 4 en indice: 4
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\03-algoritmos\busqueda-rangos\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   Valor 3: [1, 4)
 *   Insertar 4 en indice: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
