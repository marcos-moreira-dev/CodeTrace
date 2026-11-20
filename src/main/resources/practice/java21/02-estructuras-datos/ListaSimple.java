// CodeTrace | Referencia: Java 21 LTS (JDK Temurin 21).
// Desde PowerShell, en una COPIA de la carpeta java21:
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\04-estructuras\ListaSimple.java'
// Sin main: es una clase o interfaz para compilar y reutilizar.

public final class ListaSimple<T> {
    private static final class Nodo<E> {
        E dato;
        Nodo<E> siguiente;
        Nodo(E dato) { this.dato = dato; }
    }

    private Nodo<T> primero;
    private int cantidad;

    public void insertarInicio(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        nuevo.siguiente = primero;
        primero = nuevo;
        cantidad++;
    }

    public int size() {
        return cantidad;
    }
}
