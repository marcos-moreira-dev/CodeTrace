// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-sort.jar"
// Ejecutar: java -jar "$env:TEMP\ct-sort.jar"

/** Lower: primer >= objetivo. Upper: primer > objetivo. O(log n) y O(1) auxiliar. */
object LimitesOrdenados{
    fun lower(a:IntArray,x:Int):Int{
        var lo=0;var hi=a.size
        while(lo<hi){val m=lo+(hi-lo)/2;if(a[m]<x)lo=m+1 else hi=m}
        return lo
    }
    fun upper(a:IntArray,x:Int):Int{
        var lo=0;var hi=a.size
        while(lo<hi){val m=lo+(hi-lo)/2;if(a[m]<=x)lo=m+1 else hi=m}
        return lo
    }
}
