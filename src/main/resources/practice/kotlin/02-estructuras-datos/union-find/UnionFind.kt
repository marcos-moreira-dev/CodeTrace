// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/** DSU con compresión de caminos y unión por tamaño, O(alfa(n)) amortizado. */
class UnionFind(n:Int){
    private val padre=IntArray(n){it}
    private val tamanio=IntArray(n){1}
    var componentes=n
        private set
    init{require(n>0)}
    fun raiz(x:Int):Int{
        if(padre[x]!=x)padre[x]=raiz(padre[x])
        return padre[x]
    }
    fun unir(a:Int,b:Int):Boolean{
        var x=raiz(a);var y=raiz(b)
        if(x==y)return false
        if(tamanio[x]<tamanio[y]){val temporal=x;x=y;y=temporal}
        padre[y]=x;tamanio[x]+=tamanio[y];componentes--
        return true
    }
    fun conectados(a:Int,b:Int)=raiz(a)==raiz(b)
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\union-find\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: UnionFind.kt es un componente; la salida corresponde al cliente MainUnionFind.kt.
 * Salida esperada:
 *   Componentes: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
