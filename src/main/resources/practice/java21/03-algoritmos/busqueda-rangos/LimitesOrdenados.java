// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde COPIA de java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-curso-java" codetrace.practica.algoritmos.limites.MainLimitesOrdenados

package codetrace.practica.algoritmos.limites;

/**
 * lowerBound: primer indice a[i]>=objetivo.
 * upperBound: primer indice a[i]>objetivo.
 * Ambos devuelven una POSICION DE INSERCION en [0,n], no -1.
 * Precondicion: arreglo ordenado ascendentemente (con repetidos).
 * Comparaciones O(log n), memoria O(1).
 */
public final class LimitesOrdenados{
    private LimitesOrdenados(){}
    public static int lowerBound(int[] a,int x){
        int lo=0,hi=a.length;
        while(lo<hi){
            int m=lo+(hi-lo)/2;
            if(a[m]<x)lo=m+1;
            else hi=m;
        }
        return lo;
    }
    public static int upperBound(int[] a,int x){
        int lo=0,hi=a.length;
        while(lo<hi){
            int m=lo+(hi-lo)/2;
            if(a[m]<=x)lo=m+1;
            else hi=m;
        }
        return lo;
    }
}
