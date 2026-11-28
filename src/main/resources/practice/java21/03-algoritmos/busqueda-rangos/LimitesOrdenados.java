// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . LimitesOrdenados.java MainLimitesOrdenados.java
// Ejecutar: java codetrace.practica.algoritmos.limites.MainLimitesOrdenados

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . LimitesOrdenados.java MainLimitesOrdenados.java
 *   java codetrace.practica.algoritmos.limites.MainLimitesOrdenados
 * Nota: LimitesOrdenados.java es un componente; la salida corresponde al cliente MainLimitesOrdenados.java.
 * Salida esperada:
 *   Valor 3: [1, 4)
 *   Insertar 4 en indice: 4
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
