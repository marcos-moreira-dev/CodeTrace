// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . ListaDoble.java MainListaDoble.java
// Ejecutar: java codetrace.practica.estructuras.listadoble.MainListaDoble

package codetrace.practica.estructuras.listadoble;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Lista doblemente enlazada: cada nodo posee anterior y siguiente.
 * Insertar en ambos extremos O(1); recorrer O(n), memoria O(n).
 * La complejidad no presupone acceso por índice O(1).
 */
public final class ListaDoble<T> {
    private static final class Nodo<E> {
        E valor;
        Nodo<E> anterior, siguiente;
        Nodo(E valor) { this.valor = valor; }
    }
    private Nodo<T> primero, ultimo;
    private int cantidad;

    public void agregarPrimero(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        nuevo.siguiente = primero;
        if (primero == null) ultimo = nuevo;
        else primero.anterior = nuevo;
        primero = nuevo;
        cantidad++;
    }
    public void agregarUltimo(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        nuevo.anterior = ultimo;
        if (ultimo == null) primero = nuevo;
        else ultimo.siguiente = nuevo;
        ultimo = nuevo;
        cantidad++;
    }
    public T quitarUltimo() {
        if (ultimo == null) throw new NoSuchElementException("Lista vacía");
        T valor = ultimo.valor;
        ultimo = ultimo.anterior;
        if (ultimo == null) primero = null;
        else ultimo.siguiente = null;
        cantidad--;
        return valor;
    }
    public int cantidad() { return cantidad; }
    public List<T> adelante() {
        var salida = new ArrayList<T>();
        for (Nodo<T> n = primero; n != null; n = n.siguiente) salida.add(n.valor);
        return salida;
    }
    public List<T> atras() {
        var salida = new ArrayList<T>();
        for (Nodo<T> n = ultimo; n != null; n = n.anterior) salida.add(n.valor);
        return salida;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ListaDoble.java MainListaDoble.java
 *   java codetrace.practica.estructuras.listadoble.MainListaDoble
 * Nota: ListaDoble.java es un componente; la salida corresponde al cliente MainListaDoble.java.
 * Salida esperada:
 *   Adelante: [Z, A]
 *   Atrás: [A, Z]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
