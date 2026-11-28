// Tema:
// ArrayBlockingQueue implementa el buffer acotado y bloquea put/take según capacidad. Un centinela cierra el ejemplo con un consumidor.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Productor.java Consumidor.java MainBlockingQueue.java
// Ejecutar: java MainBlockingQueue

import java.util.concurrent.ArrayBlockingQueue;

public class Consumidor implements Runnable {
    private final ArrayBlockingQueue<Integer> buffer;
    public Consumidor(ArrayBlockingQueue<Integer> buffer) { this.buffer = buffer; }
    public void run() {
        try {
            int dato, suma = 0;
            while ((dato = buffer.take()) != -1) suma += dato;
            System.out.println("Suma: " + suma);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}

// Salida esperada:
// Suma: 10
