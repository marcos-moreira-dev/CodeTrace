// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . ListaDobleCircular.java MainListaDobleCircular.java
// Ejecutar: java codetrace.practica.estructuras.listadoblecircular.MainListaDobleCircular

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ListaDobleCircular.java MainListaDobleCircular.java
 *   java codetrace.practica.estructuras.listadoblecircular.MainListaDobleCircular
 * Nota: ListaDobleCircular.java es un componente; la salida corresponde al cliente MainListaDobleCircular.java.
 * Salida esperada:
 *   [2, 3, 5] / [5, 3, 2]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
