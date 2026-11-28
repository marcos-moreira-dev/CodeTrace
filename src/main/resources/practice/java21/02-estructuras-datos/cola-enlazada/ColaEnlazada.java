// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . ColaEnlazada.java MainColaEnlazada.java
// Ejecutar: java codetrace.practica.estructuras.colaenlazada.MainColaEnlazada

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ColaEnlazada.java MainColaEnlazada.java
 *   java codetrace.practica.estructuras.colaenlazada.MainColaEnlazada
 * Nota: ColaEnlazada.java es un componente; la salida corresponde al cliente MainColaEnlazada.java.
 * Salida esperada:
 *   FIFO enlazada OK
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
