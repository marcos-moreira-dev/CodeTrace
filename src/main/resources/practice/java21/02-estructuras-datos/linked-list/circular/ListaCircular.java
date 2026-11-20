// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

package codetrace.practica.estructuras.listacircular;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista simplemente enlazada circular, sin nodo terminal null.
 * tail.siguiente apunta siempre al primero si la lista no está vacía.
 * Insertar al final O(1), rotar el primero al final O(1);
 * recorrer completo O(n) limitando la visita por cantidad.
 */
public final class ListaCircular<T> {
    private static final class Nodo<E> {
        E valor; Nodo<E> siguiente;
        Nodo(E valor) { this.valor = valor; }
    }
    private Nodo<T> ultimo;
    private int cantidad;
    public void agregar(T valor) {
        Nodo<T> n = new Nodo<>(valor);
        if (ultimo == null) n.siguiente = n;
        else { n.siguiente = ultimo.siguiente; ultimo.siguiente = n; }
        ultimo = n; cantidad++;
    }
    public void rotar() {
        if (ultimo != null) ultimo = ultimo.siguiente;
    }
    public List<T> valores() {
        var r = new ArrayList<T>();
        if (ultimo == null) return r;
        Nodo<T> actual = ultimo.siguiente;
        for (int i = 0; i < cantidad; i++) {
            r.add(actual.valor);
            actual = actual.siguiente;
        }
        return r;
    }
}
