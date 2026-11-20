// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.ordenamientos.MainOrdenamientos

package codetrace.practica.ordenamientos;

import java.util.Arrays;

/**
 * Cliente de comparación de algoritmos de ordenamiento.
 *
 * <p>Ambos reciben la MISMA entrada mediante copias independientes.
 * Inserción es simple y eficaz en arreglos pequeños o casi ordenados,
 * pero su peor caso es cuadrático. Mezcla divide el problema y ofrece
 * O(n log n), a cambio de reservar un arreglo auxiliar O(n).
 *
 * <p>Para medir rendimiento se necesitarían múltiples tamaños, repeticiones
 * y calentamiento de JIT; una sola ejecución no demuestra complejidad.
 */
public final class MainOrdenamientos {
    public static void main(String[] args) {
        int[] entrada = {9, 3, 8, 1, 7, 2, 5};
        int[] insertion = entrada.clone(), merge = entrada.clone();
        Ordenadores.insercion(insertion);
        Ordenadores.mezcla(merge);
        System.out.println("Inserción: " + Arrays.toString(insertion));
        System.out.println("Mezcla:    " + Arrays.toString(merge));
        if (!Arrays.equals(insertion, merge)) throw new AssertionError("No coinciden");
    }
}
