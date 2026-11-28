// Compilar (desde esta carpeta): javac -encoding UTF-8 ListaSimple.java MainListaSimple.java
// Ejecutar: java MainListaSimple

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 ListaSimple.java MainListaSimple.java
 *   java MainListaSimple
 * Nota: ListaSimple.java es un componente; la salida corresponde al cliente MainListaSimple.java.
 * Salida esperada:
 *   Elementos insertados: 3
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
