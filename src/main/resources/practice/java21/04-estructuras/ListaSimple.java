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
