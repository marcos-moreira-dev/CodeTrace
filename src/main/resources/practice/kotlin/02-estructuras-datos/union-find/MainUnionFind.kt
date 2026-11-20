// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Cliente DSU: componentes conexas y uniones repetidas.
 *
 * Esta estructura se usa en Kruskal para evitar ciclos, no para
 * hallar rutas. Si dos elementos ya comparten representante,
 * la unión no debe modificar el contador de componentes.
 */
fun main(){
    val uf=UnionFind(5)
    check(uf.unir(0,1)&&uf.unir(1,2)&&!uf.unir(0,2))
    check(uf.conectados(0,2)&&!uf.conectados(0,4))
    check(uf.componentes==3)
    println("Componentes: "+uf.componentes)
}
