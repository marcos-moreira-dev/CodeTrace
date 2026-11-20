// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

package codetrace.practica.estructuras.colaenlazada;

import java.util.NoSuchElementException;

/**
 * Cola FIFO mediante referencias y nodos, sin capacidad fija.
 * El último nodo enlaza la siguiente inserción; el primero se retira.
 * Ambos extremos se modifican en O(1), memoria O(n).
 */
public final class ColaEnlazada<T> {
    private static final class Nodo<E> {
        E dato; Nodo<E> siguiente;
        Nodo(E dato) { this.dato = dato; }
    }
    private Nodo<T> primero, ultimo;
    private int cantidad;
    public void encolar(T dato) {
        var n = new Nodo<>(dato);
        if (ultimo == null) primero = n;
        else ultimo.siguiente = n;
        ultimo = n; cantidad++;
    }
    public T desencolar() {
        if (primero == null) throw new NoSuchElementException("Cola vacía");
        T dato = primero.dato;
        primero = primero.siguiente;
        if (primero == null) ultimo = null;
        cantidad--;
        return dato;
    }
    public int cantidad() { return cantidad; }
}
