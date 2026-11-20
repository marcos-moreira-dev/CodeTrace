// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar/Comprobar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-biblioteca" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-biblioteca" codetrace.practica.heaps.MainColaPrioridad

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
