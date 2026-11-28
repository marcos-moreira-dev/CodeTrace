// Tema:
// Modelo clásico de sincronización condicional: tres participantes disponen de un recurso diferente. El agente habilita solo al que posee el recurso complementario y espera confirmación antes de continuar. Es un problema abstracto de semáforos, no un procedimiento físico.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 MesaIngredientes.java Agente.java Fumador.java MainFumadores.java
// Ejecutar: java MainFumadores

public class Fumador implements Runnable {
    private final MesaIngredientes mesa;
    private final int id;
    public Fumador(MesaIngredientes mesa, int id) { this.mesa = mesa; this.id = id; }
    public void run() {
        try { mesa.consumir(id); }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}

// Salida esperada:
// Turnos completados: 3
