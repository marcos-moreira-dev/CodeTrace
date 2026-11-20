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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.algoritmos.limites.MainLimitesOrdenados
 * Nota: LimitesOrdenados.java es un componente; la salida corresponde al cliente MainLimitesOrdenados.java.
 * Salida esperada:
 *   Valor 3: [1, 4)
 *   Insertar 4 en indice: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
