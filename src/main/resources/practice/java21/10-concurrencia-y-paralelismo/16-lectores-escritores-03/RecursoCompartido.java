// Tema:
// ReentrantReadWriteLock permite varios lectores o un escritor exclusivo. La opción fair ofrece una política aproximadamente por llegada, no un orden FIFO absoluto.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 RecursoCompartido.java MainLectoresEscritoresEquitativo.java
// Ejecutar: java MainLectoresEscritoresEquitativo

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RecursoCompartido {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private int valor;

    public int leer() {
        lock.readLock().lock();
        try { return valor; }
        finally { lock.readLock().unlock(); }
    }

    public void escribir() {
        lock.writeLock().lock();
        try { valor++; }
        finally { lock.writeLock().unlock(); }
    }
}

// Salida esperada:
// Valor final: 2
