// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde COPIA de java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-curso-java" codetrace.practica.algoritmos.insercionbinaria.MainInsercionBinaria

package codetrace.practica.algoritmos.insercionbinaria;

/**
 * Insertion Sort con busqueda binaria de la POSICION de insercion.
 * upperBound permite insertar DESPUES de claves iguales y mantener
 * estabilidad. Comparaciones O(n log n), movimientos O(n²);
 * tiempo total sigue siendo O(n²) en el peor caso y auxiliar O(1).
 */
public final class InsercionBinaria{
    private InsercionBinaria(){}
    public static void ordenar(int[] a){
        for(int i=1;i<a.length;i++){
            int clave=a[i],lo=0,hi=i;
            while(lo<hi){
                int medio=lo+(hi-lo)/2;
                if(a[medio]<=clave)lo=medio+1;
                else hi=medio;
            }
            for(int j=i;j>lo;j--)a[j]=a[j-1];
            a[lo]=clave;
        }
    }
}
