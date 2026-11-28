// Tema:
// Prioridad de lectores: el primer lector adquiere el recurso, el último lo libera. Un flujo continuo de lectores puede aplazar al escritor. La salida final no presupone orden de ejecución.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 RecursoCompartido.java Lector.java Escritor.java MainPrioridadLectores.java
// Ejecutar: java MainPrioridadLectores

public class Lector implements Runnable {
    private final RecursoCompartido recurso;
    public Lector(RecursoCompartido recurso) { this.recurso = recurso; }
    public void run() {
        recurso.leer();
    }
}

// Salida esperada:
// Valor final: 2
