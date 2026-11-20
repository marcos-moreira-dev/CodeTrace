// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

package codetrace.practica.estructuras.listadoblecircular;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista DOBLE y CIRCULAR: first.prev == last y last.next == first.
 * Estructura mínima para estudiar enlaces bidireccionales y ciclos.
 * Inserción O(1); vista hacia delante/atrás O(n).
 */
public final class ListaDobleCircular<T> {
    private static final class Nodo<E> {
        E valor; Nodo<E> siguiente, anterior;
        Nodo(E valor) { this.valor = valor; }
    }
    private Nodo<T> ultimo;
    private int cantidad;
    public void agregar(T valor) {
        Nodo<T> n = new Nodo<>(valor);
        if (ultimo == null) {
            n.siguiente = n; n.anterior = n;
        } else {
            Nodo<T> primero = ultimo.siguiente;
            n.anterior = ultimo; n.siguiente = primero;
            ultimo.siguiente = n; primero.anterior = n;
        }
        ultimo = n; cantidad++;
    }
    public List<T> adelante() {
        var r = new ArrayList<T>();
        if (ultimo == null) return r;
        var actual = ultimo.siguiente;
        for (int i=0;i<cantidad;i++,actual=actual.siguiente) r.add(actual.valor);
        return r;
    }
    public List<T> atras() {
        var r = new ArrayList<T>();
        if (ultimo == null) return r;
        var actual = ultimo;
        for (int i=0;i<cantidad;i++,actual=actual.anterior) r.add(actual.valor);
        return r;
    }
}
