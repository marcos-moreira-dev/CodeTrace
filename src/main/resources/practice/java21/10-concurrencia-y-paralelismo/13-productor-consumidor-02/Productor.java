// Tema:
// ArrayBlockingQueue implementa el buffer acotado y bloquea put/take según capacidad. Un centinela cierra el ejemplo con un consumidor.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Productor.java Consumidor.java MainBlockingQueue.java
// Ejecutar: java MainBlockingQueue

import java.util.concurrent.ArrayBlockingQueue;

public class Productor implements Runnable {
    private final ArrayBlockingQueue<Integer> buffer;
    public Productor(ArrayBlockingQueue<Integer> buffer) { this.buffer = buffer; }
    public void run() {
        try {
            for (int n = 1; n <= 4; n++) buffer.put(n);
            buffer.put(-1); // Centinela fuera del dominio de datos.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}

// Salida esperada:
// Suma: 10
