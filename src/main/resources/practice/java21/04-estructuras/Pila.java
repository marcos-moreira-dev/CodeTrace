// CodeTrace | Referencia: Java 21 LTS (JDK Temurin 21).
// Desde PowerShell, en una COPIA de la carpeta java21:
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\04-estructuras\Pila.java'
// Sin main: es una clase o interfaz para compilar y reutilizar.

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
