// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-sort.jar"
// Ejecutar: java -jar "$env:TEMP\ct-sort.jar"

/**
 * Binary insertion busca posicion por upperBound y desplaza los
 * datos. O(n log n) comparaciones, O(n²) movimientos, tiempo total
 * O(n²), memoria O(1), estable si inserta despues de empates.
 */
object InsercionBinaria{
    fun ordenar(a:IntArray){
        for(i in 1 until a.size){
            val x=a[i];var lo=0;var hi=i
            while(lo<hi){
                val m=lo+(hi-lo)/2
                if(a[m]<=x)lo=m+1 else hi=m
            }
            for(j in i downTo lo+1)a[j]=a[j-1]
            a[lo]=x
        }
    }
}
