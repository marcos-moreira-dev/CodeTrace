// Tema:
// Prioridad de escritores: el primer escritor cierra la entrada y el último la reabre; lectores activos pueden terminar. No garantiza equidad absoluta y puede aplazar lectores.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 RecursoCompartido.java Lector.java Escritor.java MainPrioridadEscritores.java
// Ejecutar: java MainPrioridadEscritores

import java.util.concurrent.Semaphore;

public class RecursoCompartido {
    private final Semaphore entrada = new Semaphore(1, true);
    private final Semaphore mutexLectores = new Semaphore(1);
    private final Semaphore mutexEscritores = new Semaphore(1);
    private final Semaphore recurso = new Semaphore(1);
    private int lectores, escritores, valor;

    public int leer() {
        // Los escritores pendientes cierran la puerta de entrada a nuevos lectores.
        entrada.acquireUninterruptibly();
        try {
            mutexLectores.acquireUninterruptibly();
            try { if (++lectores == 1) recurso.acquireUninterruptibly(); }
            finally { mutexLectores.release(); }
        } finally { entrada.release(); }
        try { return valor; }
        finally {
            mutexLectores.acquireUninterruptibly();
            try { if (--lectores == 0) recurso.release(); }
            finally { mutexLectores.release(); }
        }
    }

    public void escribir() {
        mutexEscritores.acquireUninterruptibly();
        try { if (++escritores == 1) entrada.acquireUninterruptibly(); }
        finally { mutexEscritores.release(); }
        recurso.acquireUninterruptibly();
        try { valor++; }
        finally {
            recurso.release();
            mutexEscritores.acquireUninterruptibly();
            try { if (--escritores == 0) entrada.release(); }
            finally { mutexEscritores.release(); }
        }
    }
}

// Salida esperada:
// Valor final: 2
