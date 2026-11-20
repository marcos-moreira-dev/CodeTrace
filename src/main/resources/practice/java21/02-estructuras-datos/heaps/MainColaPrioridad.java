// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.heaps.MainColaPrioridad
package codetrace.practica.heaps;

/**
 * Cliente de una cola de prioridad implementada con un heap binario.
 *
 * <p>Una cola FIFO atiende por llegada. Un heap, en cambio, permite extraer
 * siempre el elemento de menor prioridad numérica. En este ejercicio la
 * implementación está separada para poder estudiar los reajustes del
 * arreglo y las fórmulas de índices. Insertar y extraer cuestan O(log n);
 * consultar la raíz sería O(1).
 */
public final class MainColaPrioridad {
    public static void main(String[] args) {
        MinHeapDidactico heap = new MinHeapDidactico();
        for (int prioridad : new int[]{7, 2, 5, 1, 9}) heap.insertar(prioridad);
        while (!heap.vacio()) System.out.println(heap.extraerMinimo());
    }
}

/*
 * Salida esperada de consola (una prioridad por línea):
 *   1
 *   2
 *   5
 *   7
 *   9
 * El min-heap extrae de menor a mayor, no en orden FIFO.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.heaps.MainColaPrioridad
 * Salida esperada:
 *   1
 *   2
 *   5
 *   7
 *   9
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
