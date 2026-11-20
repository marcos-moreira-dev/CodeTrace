// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

package codetrace.practica.estructuras.deque;

import java.util.NoSuchElementException;

/**
 * Deque = double-ended queue: inserción y extracción en ambos extremos.
 * Cada nodo se enlaza hacia delante y atrás; las 4 operaciones O(1).
 * No equivale a Stack ni FIFO: permite ambos comportamientos.
 */
public final class DequeEnlazado<T> {
    private static final class Nodo<E> {
        E dato; Nodo<E> previo, siguiente;
        Nodo(E dato) { this.dato = dato; }
    }
    private Nodo<T> primero, ultimo;
    private int cantidad;
    public void insertarPrimero(T dato) {
        var n = new Nodo<>(dato);
        n.siguiente = primero;
        if (primero == null) ultimo = n; else primero.previo = n;
        primero = n; cantidad++;
    }
    public void insertarUltimo(T dato) {
        var n = new Nodo<>(dato);
        n.previo = ultimo;
        if (ultimo == null) primero = n; else ultimo.siguiente = n;
        ultimo = n; cantidad++;
    }
    public T quitarPrimero() {
        if (primero == null) throw new NoSuchElementException("Deque vacío");
        T valor = primero.dato;
        primero = primero.siguiente;
        if (primero == null) ultimo = null; else primero.previo = null;
        cantidad--; return valor;
    }
    public T quitarUltimo() {
        if (ultimo == null) throw new NoSuchElementException("Deque vacío");
        T valor = ultimo.dato;
        ultimo = ultimo.previo;
        if (ultimo == null) primero = null; else ultimo.siguiente = null;
        cantidad--; return valor;
    }
    public int cantidad() { return cantidad; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.estructuras.deque.MainDeque
 * Nota: DequeEnlazado.java es un componente; la salida corresponde al cliente MainDeque.java.
 * Salida esperada:
 *   Operaciones en ambos extremos OK
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
