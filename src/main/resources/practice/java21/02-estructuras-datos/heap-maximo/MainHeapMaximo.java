// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.estructuras.heapmaximo.MainHeapMaximo

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
