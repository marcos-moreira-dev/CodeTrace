// Tema:
// Modelo clásico de sincronización condicional: tres participantes disponen de un recurso diferente. El agente habilita solo al que posee el recurso complementario y espera confirmación antes de continuar. Es un problema abstracto de semáforos, no un procedimiento físico.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 MesaIngredientes.java Agente.java Fumador.java MainFumadores.java
// Ejecutar: java MainFumadores

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class MesaIngredientes {
    private final Semaphore[] turnos = {new Semaphore(0), new Semaphore(0), new Semaphore(0)};
    private final Semaphore terminado = new Semaphore(0);
    private final AtomicInteger total = new AtomicInteger();

    public void ofrecer(int id) throws InterruptedException {
        turnos[id].release();
        terminado.acquire();
    }

    public void consumir(int id) throws InterruptedException {
        turnos[id].acquire();
        total.incrementAndGet();
        terminado.release();
    }
    public int total() { return total.get(); }
}

// Salida esperada:
// Turnos completados: 3
