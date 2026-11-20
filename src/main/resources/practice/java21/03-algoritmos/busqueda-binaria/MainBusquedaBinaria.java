// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde la raíz java21 (PowerShell):
// javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-curso-java" MainBusquedaBinaria

import java.util.Arrays;

/**
 * Cliente: búsqueda binaria y precondición de ordenamiento.
 *
 * <p>El rango candidato se reduce a la mitad por comparación,
 * manteniendo el invariante de que un objetivo existente solo
 * podría permanecer dentro del intervalo [izquierda,derecha].
 * O(log n) comparaciones en arreglo ordenado, O(1) memoria.
 *
 * <p>Ni la búsqueda binaria manual ni Arrays.binarySearch() deben
 * aplicarse alegremente a datos no ordenados. Se contrasta el
 * resultado con la biblioteca estándar y un caso inexistente.
 */
public final class MainBusquedaBinaria {
    public static void main(String[] args) {
        int[] asc = {1, 3, 4, 7, 9, 12, 16};
        int esperado = Arrays.binarySearch(asc, 9);
        int real = BusquedaBinaria.buscar(asc, 9);
        if (real != esperado || BusquedaBinaria.buscar(asc, 8) != -1)
            throw new AssertionError("Búsqueda incorrecta");
        System.out.println("Índice: " + real + "; inexistente: -1");
    }
}
