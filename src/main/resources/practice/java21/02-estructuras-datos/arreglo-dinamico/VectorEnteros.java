// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainVector.java VectorEnteros.java
// Ejecutar: java codetrace.practica.vector.MainVector

package codetrace.practica.vector;

import java.util.Arrays;

/**
 * Arreglo dinámico especializado en int para visualizar crecimiento.
 *
 * <p>El tamaño lógico es diferente a la capacidad reservada. Cuando no
 * queda espacio, se duplica la capacidad y se copian los valores previos.
 * agregar es O(1) amortizado, aunque una operación de redimensionamiento
 * individual cuesta O(n). acceder por índice cuesta O(1).
 */
public final class VectorEnteros {
    private int[] almacenamiento = new int[2];
    private int cantidad;

    public void agregar(int valor) {
        if (cantidad == almacenamiento.length)
            almacenamiento = Arrays.copyOf(almacenamiento, almacenamiento.length * 2);
        almacenamiento[cantidad++] = valor;
    }

    public int obtener(int indice) {
        if (indice < 0 || indice >= cantidad) throw new IndexOutOfBoundsException(indice);
        return almacenamiento[indice];
    }

    public int tamaño() { return cantidad; }
    public int capacidad() { return almacenamiento.length; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainVector.java VectorEnteros.java
 *   java codetrace.practica.vector.MainVector
 * Nota: VectorEnteros.java es un componente; la salida corresponde al cliente MainVector.java.
 * Salida esperada:
 *   Elementos: 6
 *   Capacidad: 8
 *   Tercero: 30
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
