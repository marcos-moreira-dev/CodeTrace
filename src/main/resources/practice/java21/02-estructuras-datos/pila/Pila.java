// Compilar (desde esta carpeta): javac -encoding UTF-8 MainPila.java Pila.java
// Ejecutar: java MainPila

import java.util.NoSuchElementException;

public final class Pila<T> {
    private final Object[] datos = new Object[16];
    private int cantidad;

    public void apilar(T valor) {
        if (cantidad == datos.length) throw new IllegalStateException("Llena");
        datos[cantidad++] = valor;
    }

    @SuppressWarnings("unchecked")
    public T desapilar() {
        if (cantidad == 0) throw new NoSuchElementException();
        T valor = (T) datos[--cantidad];
        datos[cantidad] = null;
        return valor;
    }

    public int size() {
        return cantidad;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 MainPila.java Pila.java
 *   java MainPila
 * Nota: Pila.java es un componente; la salida corresponde al cliente MainPila.java.
 * Salida esperada:
 *   LIFO comprobado
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
