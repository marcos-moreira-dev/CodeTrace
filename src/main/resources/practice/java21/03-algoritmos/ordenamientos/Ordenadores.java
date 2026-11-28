// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . MainOrdenamientos.java Ordenadores.java
// Ejecutar: java codetrace.practica.ordenamientos.MainOrdenamientos

package codetrace.practica.ordenamientos;

/**
 * Cinco ordenamientos clasicos para contrastar complejidad y estabilidad.
 *
 * <p>Insertion Sort mantiene un prefijo ordenado: O(n^2) en peor caso,
 * O(n) con la entrada ya ordenada y O(1) memoria auxiliar.
 * Merge Sort divide y combina: O(n log n) en todos los casos y
 * O(n) memoria adicional; ambos preservan el orden relativo de iguales.
 */
public final class Ordenadores {
    private Ordenadores() {}

    public static void insercion(int[] datos) {
        for (int i = 1; i < datos.length; i++) {
            int elegido = datos[i], j = i - 1;
            while (j >= 0 && datos[j] > elegido) {
                datos[j + 1] = datos[j];
                j--;
            }
            datos[j + 1] = elegido;
        }
    }

    public static void mezcla(int[] datos) {
        int[] auxiliar = new int[datos.length];
        ordenar(datos, auxiliar, 0, datos.length);
    }

    private static void ordenar(int[] a, int[] temp, int lo, int hi) {
        if (hi - lo <= 1) return;
        int medio = lo + (hi - lo) / 2;
        ordenar(a, temp, lo, medio);
        ordenar(a, temp, medio, hi);
        int i = lo, j = medio;
        for (int k = lo; k < hi; k++) {
            if (j == hi || (i < medio && a[i] <= a[j])) temp[k] = a[i++];
            else temp[k] = a[j++];
        }
        System.arraycopy(temp, lo, a, lo, hi - lo);
    }
    /**
     * Burbuja optimizada: frontera del ultimo intercambio. Estable.
     * Mejor O(n); peor O(n^2); memoria O(1).
     */
    public static void burbuja(int[] a){
        int limite=a.length;
        while(limite>1){
            int ultimo=0;
            for(int i=1;i<limite;i++){
                if(a[i-1]>a[i]){
                    int t=a[i];a[i]=a[i-1];a[i-1]=t;
                    ultimo=i;
                }
            }
            limite=ultimo;
        }
    }

    /** HeapSort in-place, O(n log n), no estable, memoria O(1). */
    public static void heap(int[] a){
        for(int i=a.length/2-1;i>=0;i--)hundir(a,a.length,i);
        for(int fin=a.length-1;fin>0;fin--){
            int t=a[0];a[0]=a[fin];a[fin]=t;
            hundir(a,fin,0);
        }
    }
    private static void hundir(int[] a,int n,int i){
        while(true){
            int mayor=i,iz=2*i+1,de=iz+1;
            if(iz<n&&a[iz]>a[mayor])mayor=iz;
            if(de<n&&a[de]>a[mayor])mayor=de;
            if(mayor==i)return;
            int t=a[i];a[i]=a[mayor];a[mayor]=t;
            i=mayor;
        }
    }

    /**
     * QuickSort con pivote final: medio O(n log n), peor O(n^2)
     * con entrada ya ordenada. La pila en peor caso es O(n).
     * No estable. Demo determinista para estudiar la particion.
     */
    public static void rapido(int[] a){quick(a,0,a.length-1);}
    private static void quick(int[] a,int lo,int hi){
        if(lo>=hi)return;
        int pivote=a[hi],i=lo;
        for(int j=lo;j<hi;j++){
            if(a[j]<=pivote){
                int t=a[i];a[i]=a[j];a[j]=t;
                i++;
            }
        }
        int t=a[i];a[i]=a[hi];a[hi]=t;
        quick(a,lo,i-1);
        quick(a,i+1,hi);
    }

}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainOrdenamientos.java Ordenadores.java
 *   java codetrace.practica.ordenamientos.MainOrdenamientos
 * Nota: Ordenadores.java es un componente; la salida corresponde al cliente MainOrdenamientos.java.
 * Salida esperada:
 *   Insercion: [1, 2, 3, 5, 7, 8, 9]
 *   Burbuja: [1, 2, 3, 5, 7, 8, 9]
 *   Mezcla: [1, 2, 3, 5, 7, 8, 9]
 *   HeapSort: [1, 2, 3, 5, 7, 8, 9]
 *   QuickSort: [1, 2, 3, 5, 7, 8, 9]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
