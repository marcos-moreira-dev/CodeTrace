// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: ejecutar desde el archivo cliente del mismo ejercicio

package codetrace.practica.ordenamientos;

/**
 * Dos ordenamientos que permiten contrastar estrategias algorítmicas.
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
}
