// Tema:
// Barbero dormilón: una cola representa las sillas de espera. offer rechaza si no hay sitio; take espera si no hay clientes. Se llena la sala antes de iniciar el servicio para que el rechazo sea reproducible; un centinela cierra la barbería.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Barberia.java Barbero.java Cliente.java MainBarberia.java
// Ejecutar: java MainBarberia

public class Barbero implements Runnable {
    private final Barberia barberia;
    public Barbero(Barberia barberia) { this.barberia = barberia; }
    public void run() {
        try {
            while (barberia.siguiente() != -1) barberia.cortar();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}

// Salida esperada:
// Cliente 1: true
// Cliente 2: true
// Cliente 3: false
// Atendidos: 2
