// Tema:
// Prioridad de lectores: el primer lector adquiere el recurso, el último lo libera. Un flujo continuo de lectores puede aplazar al escritor. La salida final no presupone orden de ejecución.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 RecursoCompartido.java Lector.java Escritor.java MainPrioridadLectores.java
// Ejecutar: java MainPrioridadLectores

import java.util.concurrent.Semaphore;

public class RecursoCompartido {
    private final Semaphore mutex = new Semaphore(1), recurso = new Semaphore(1);
    private int lectores, valor;

    public int leer() {
        mutex.acquireUninterruptibly();
        try {
            if (++lectores == 1) recurso.acquireUninterruptibly();
        } finally { mutex.release(); }
        try { return valor; }
        finally {
            mutex.acquireUninterruptibly();
            try { if (--lectores == 0) recurso.release(); }
            finally { mutex.release(); }
        }
    }

    public void escribir() {
        recurso.acquireUninterruptibly();
        try { valor++; }
        finally { recurso.release(); }
    }
}

// Salida esperada:
// Valor final: 2
