// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/** Max heap binario, O(log n) al insertar y extraer, sobre MutableList. */
class HeapMaximo{
    private val a=mutableListOf<Int>()
    fun insertar(v:Int){
        a.add(v)
        var i=a.lastIndex
        while(i>0){
            val p=(i-1)/2
            if(a[p]>=a[i])break
            val t=a[p];a[p]=a[i];a[i]=t;i=p
        }
    }
    fun extraer():Int{
        if(a.isEmpty())throw NoSuchElementException("Heap vacío")
        val resultado=a[0]
        val ultimo=a.removeAt(a.lastIndex)
        if(a.isNotEmpty()){
            a[0]=ultimo;var i=0
            while(true){
                var m=i;val izq=2*i+1;val der=izq+1
                if(izq<a.size&&a[izq]>a[m])m=izq
                if(der<a.size&&a[der]>a[m])m=der
                if(m==i)break
                val t=a[i];a[i]=a[m];a[m]=t;i=m
            }
        }
        return resultado
    }
}
