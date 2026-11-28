// Tema:
// Modelo clásico de sincronización condicional: tres participantes disponen de un recurso diferente. El agente habilita solo al que posee el recurso complementario y espera confirmación antes de continuar. Es un problema abstracto de semáforos, no un procedimiento físico.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 MesaIngredientes.java Agente.java Fumador.java MainFumadores.java
// Ejecutar: java MainFumadores

public class Agente implements Runnable {
    private final MesaIngredientes mesa;
    public Agente(MesaIngredientes mesa) { this.mesa = mesa; }
    public void run() {
        try {
            for (int i = 0; i < 3; i++) mesa.ofrecer(i);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}

// Salida esperada:
// Turnos completados: 3
