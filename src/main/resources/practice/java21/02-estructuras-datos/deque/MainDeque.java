// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.estructuras.deque.MainDeque

package codetrace.practica.estructuras.deque;

/**
 * Cliente Deque: una doble cola soporta tanto FIFO como LIFO.
 *
 * <p>Deque no implica dos colas independientes; es una misma secuencia
 * con operaciones sobre ambos extremos. La implementación aquí es
 * manual para ver enlaces, pero Java ofrece ArrayDeque para la mayoría
 * de usos. No debe confundirse ArrayDeque con una cola concurrente.
 */
public final class MainDeque {
    public static void main(String[] args) {
        var q = new DequeEnlazado<String>();
        q.insertarPrimero("B"); q.insertarPrimero("A"); q.insertarUltimo("C");
        if (!"A".equals(q.quitarPrimero()) || !"C".equals(q.quitarUltimo())
                || !"B".equals(q.quitarUltimo()) || q.cantidad()!=0)
            throw new AssertionError("Deque");
        System.out.println("Operaciones en ambos extremos OK");
    }
}

/*
 * Salida esperada de consola:
 *   Operaciones en ambos extremos OK
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
