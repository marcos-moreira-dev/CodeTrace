// Tema:
// Phaser coordina fases y permite registrar o retirar participantes. Cada trabajador llega a la primera fase y después se retira.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 PhaserDemo.java
// Ejecutar: java PhaserDemo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class PhaserDemo {
    static Thread iniciar(Runnable tarea) {
        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true);
        hilo.start();
        return hilo;
    }

    public static void main(String[] args) throws Exception {
        Phaser fases = new Phaser(1);
        fases.bulkRegister(2);
        Runnable tarea = () -> {
            try {
                int fase = fases.arrive();
                fases.awaitAdvanceInterruptibly(fase, 3, TimeUnit.SECONDS);
                fases.arriveAndDeregister();
            } catch (Exception e) { throw new IllegalStateException(e); }
        };
        Thread a = iniciar(tarea), b = iniciar(tarea);
        int fase = fases.arrive();
        fases.awaitAdvanceInterruptibly(fase, 3, TimeUnit.SECONDS);
        a.join(3000);
        b.join(3000);
        if (a.isAlive() || b.isAlive()) throw new IllegalStateException("Hilos activos");
        System.out.println("Fase: " + fases.getPhase());
        System.out.println("Registrados: " + fases.getRegisteredParties());
        fases.arriveAndDeregister();
    }
}

// Salida esperada:
// Fase: 1
// Registrados: 1
