// Tema:
// Prioridad de escritores: el primer escritor cierra la entrada y el último la reabre; lectores activos pueden terminar. No garantiza equidad absoluta y puede aplazar lectores.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 RecursoCompartido.java Lector.java Escritor.java MainPrioridadEscritores.java
// Ejecutar: java MainPrioridadEscritores

public class Lector implements Runnable {
    private final RecursoCompartido recurso;
    public Lector(RecursoCompartido recurso) { this.recurso = recurso; }
    public void run() {
        recurso.leer();
    }
}

// Salida esperada:
// Valor final: 2
