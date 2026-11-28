// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . HeapMaximo.java MainHeapMaximo.java
// Ejecutar: java codetrace.practica.estructuras.heapmaximo.MainHeapMaximo

package codetrace.practica.estructuras.heapmaximo;

import java.util.List;

/**
 * Cliente heap máximo versus cola de prioridad mínima.
 *
 * <p>Una pila extrae el último, una cola FIFO el primero, un heap
 * máximo el elemento NUMÉRICAMENTE mayor. La propiedad de heap
 * no ordena el arreglo globalmente. Permite insertar y extraer
 * eficientemente; para ordenar todo, extraemos repetidamente.
 */
public final class MainHeapMaximo {
    public static void main(String[] args) {
        var h=new HeapMaximo();
        for(int x:List.of(4,1,9,7,2))h.insertar(x);
        for(int esperado:List.of(9,7,4,2,1))
            if(h.extraer()!=esperado)throw new AssertionError("Orden heap");
        if(!h.vacio())throw new AssertionError("Restos");
        System.out.println("Max heap verificado");
    }
}

/*
 * Salida esperada de consola:
 *   Max heap verificado
 * La extracción validada es 9, 7, 4, 2, 1; no se imprimen esos valores.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . HeapMaximo.java MainHeapMaximo.java
 *   java codetrace.practica.estructuras.heapmaximo.MainHeapMaximo
 * Salida esperada:
 *   Max heap verificado
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
