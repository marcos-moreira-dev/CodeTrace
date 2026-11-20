// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde COPIA de java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-curso-java" codetrace.practica.algoritmos.burbuja.MainBurbuja

package codetrace.practica.algoritmos.burbuja;

/**
 * Bubble Sort optimizado por frontera del ultimo intercambio.
 * Invariante: tras una pasada, el sufijo posterior al ultimo
 * intercambio ya esta ordenado. Intercambia SOLO ante > y por ello
 * conserva el orden relativo de claves iguales (estable).
 * Mejor Θ(n), promedio/peor Θ(n²), auxiliar Θ(1).
 */
public final class BurbujaOptimizada {
    private BurbujaOptimizada() {}
    public static int ordenar(int[] a) {
        int comparaciones=0, limite=a.length;
        while(limite>1){
            int ultimo=0;
            for(int j=1;j<limite;j++){
                comparaciones++;
                if(a[j-1]>a[j]){
                    int v=a[j-1];a[j-1]=a[j];a[j]=v;
                    ultimo=j;
                }
            }
            limite=ultimo;
        }
        return comparaciones;
    }
}
