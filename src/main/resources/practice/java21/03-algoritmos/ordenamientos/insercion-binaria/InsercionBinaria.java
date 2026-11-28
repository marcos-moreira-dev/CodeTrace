// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . InsercionBinaria.java MainInsercionBinaria.java
// Ejecutar: java codetrace.practica.algoritmos.insercionbinaria.MainInsercionBinaria

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . InsercionBinaria.java MainInsercionBinaria.java
 *   java codetrace.practica.algoritmos.insercionbinaria.MainInsercionBinaria
 * Nota: InsercionBinaria.java es un componente; la salida corresponde al cliente MainInsercionBinaria.java.
 * Salida esperada:
 *   Insercion binaria: [1, 2, 3, 3, 5]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
