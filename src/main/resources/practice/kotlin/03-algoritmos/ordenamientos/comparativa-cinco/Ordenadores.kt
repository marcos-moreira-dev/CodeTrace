// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-sort.jar"
// Ejecutar: java -jar "$env:TEMP\ct-sort.jar"

/**
 * Cinco ordenamientos comparables con los de Java y el libro.
 * Insertion/Bubble: mejor O(n), peor O(n²), estables, O(1) auxiliar.
 * Merge: O(n log n), O(n) auxiliar, estable por <= en empate.
 * Heap: O(n log n), O(1) auxiliar, no estable.
 * Quick pivote final: esperado O(n log n), peor O(n²), no estable.
 */
object Ordenadores{
    fun insercion(a:IntArray){
        for(i in 1 until a.size){
            val v=a[i];var j=i-1
            while(j>=0&&a[j]>v){a[j+1]=a[j];j--}
            a[j+1]=v
        }
    }
    fun burbuja(a:IntArray){
        var limite=a.size
        while(limite>1){
            var ultimo=0
            for(j in 1 until limite)if(a[j-1]>a[j]){
                val t=a[j-1];a[j-1]=a[j];a[j]=t;ultimo=j
            }
            limite=ultimo
        }
    }
    fun mezcla(a:IntArray){
        val tmp=IntArray(a.size)
        fun sort(lo:Int,hi:Int){
            if(hi-lo<=1)return
            val m=lo+(hi-lo)/2
            sort(lo,m);sort(m,hi)
            var i=lo;var j=m
            for(k in lo until hi){
                if(j==hi||(i<m&&a[i]<=a[j]))tmp[k]=a[i++]
                else tmp[k]=a[j++]
            }
            for(k in lo until hi)a[k]=tmp[k]
        }
        sort(0,a.size)
    }
    fun heap(a:IntArray){
        fun hundir(n:Int,inicio:Int){
            var i=inicio
            while(true){
                var mayor=i;val iz=2*i+1;val de=iz+1
                if(iz<n&&a[iz]>a[mayor])mayor=iz
                if(de<n&&a[de]>a[mayor])mayor=de
                if(mayor==i)return
                val t=a[i];a[i]=a[mayor];a[mayor]=t;i=mayor
            }
        }
        for(i in a.size/2-1 downTo 0)hundir(a.size,i)
        for(fin in a.lastIndex downTo 1){
            val t=a[0];a[0]=a[fin];a[fin]=t
            hundir(fin,0)
        }
    }
    fun rapido(a:IntArray){
        fun sort(lo:Int,hi:Int){
            if(lo>=hi)return
            val p=a[hi];var i=lo
            for(j in lo until hi)if(a[j]<=p){
                val t=a[i];a[i]=a[j];a[j]=t;i++
            }
            val t=a[i];a[i]=a[hi];a[hi]=t
            sort(lo,i-1);sort(i+1,hi)
        }
        sort(0,a.lastIndex)
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\03-algoritmos\ordenamientos\comparativa-cinco\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: Ordenadores.kt es un componente; la salida corresponde al cliente MainOrdenamientos.kt.
 * Salida esperada:
 *   Insercion: [1, 2, 3, 5, 7, 8, 9]
 *   Burbuja: [1, 2, 3, 5, 7, 8, 9]
 *   Mezcla: [1, 2, 3, 5, 7, 8, 9]
 *   HeapSort: [1, 2, 3, 5, 7, 8, 9]
 *   QuickSort: [1, 2, 3, 5, 7, 8, 9]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
