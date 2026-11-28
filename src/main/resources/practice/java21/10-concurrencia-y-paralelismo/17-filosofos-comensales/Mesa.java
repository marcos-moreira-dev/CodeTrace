// Tema:
// Cinco filósofos comparten cinco tenedores. Cada uno toma sus dos tenedores en orden creciente de identificador, rompiendo la espera circular. El resultado final no depende del orden de comida.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Mesa.java Filosofo.java MainFilosofos.java
// Ejecutar: java MainFilosofos

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {
    private final ReentrantLock[] tenedores;
    private final AtomicInteger comidas = new AtomicInteger();

    public Mesa(int n) {
        if (n < 2) throw new IllegalArgumentException("Participantes");
        tenedores = new ReentrantLock[n];
        for (int i = 0; i < n; i++) tenedores[i] = new ReentrantLock();
    }

    public void comer(int id) throws InterruptedException {
        int vecino = (id + 1) % tenedores.length;
        int menor = Math.min(id, vecino), mayor = Math.max(id, vecino);
        tenedores[menor].lockInterruptibly();
        try {
            tenedores[mayor].lockInterruptibly();
            try { comidas.incrementAndGet(); }
            finally { tenedores[mayor].unlock(); }
        } finally { tenedores[menor].unlock(); }
    }
    public int comidas() { return comidas.get(); }
}

// Salida esperada:
// Comidas: 5
