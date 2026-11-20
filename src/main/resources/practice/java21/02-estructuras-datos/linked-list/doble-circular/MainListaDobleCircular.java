// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.estructuras.listadoblecircular.MainListaDobleCircular

package codetrace.practica.estructuras.listadoblecircular;

import java.util.List;

/**
 * Cliente Linked List doble circular, inspirado en las variantes de
 * tu repositorio TDAS. Cada nodo mantiene dos referencias, y ambos
 * extremos se conectan entre sí; no existe el centinela null cuando
 * la lista está poblada. Contrastar con lista circular simple y doble
 * lineal. Todo recorrido exige una condición de terminación explícita.
 */
public final class MainListaDobleCircular {
    public static void main(String[] args) {
        var l = new ListaDobleCircular<Integer>();
        l.agregar(2); l.agregar(3); l.agregar(5);
        if (!l.adelante().equals(List.of(2,3,5))
                || !l.atras().equals(List.of(5,3,2)))
            throw new AssertionError("Enlaces inversos");
        System.out.println(l.adelante() + " / " + l.atras());
    }
}

/*
 * Salida esperada de consola:
 *   [2, 3, 5] / [5, 3, 2]
 * Los dos recorridos recorren la misma lista en sentidos opuestos.
 */
