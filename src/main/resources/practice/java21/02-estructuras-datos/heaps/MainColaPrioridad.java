// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainColaPrioridad.java MinHeapDidactico.java
// Ejecutar: java codetrace.practica.heaps.MainColaPrioridad
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
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainColaPrioridad.java MinHeapDidactico.java
 *   java codetrace.practica.heaps.MainColaPrioridad
 * Salida esperada:
 *   1
 *   2
 *   5
 *   7
 *   9
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
