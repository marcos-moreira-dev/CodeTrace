// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
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
