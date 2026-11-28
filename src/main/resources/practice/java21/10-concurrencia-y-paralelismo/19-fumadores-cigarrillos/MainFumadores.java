// Tema:
// Modelo clásico de sincronización condicional: tres participantes disponen de un recurso diferente. El agente habilita solo al que posee el recurso complementario y espera confirmación antes de continuar. Es un problema abstracto de semáforos, no un procedimiento físico.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 MesaIngredientes.java Agente.java Fumador.java MainFumadores.java
// Ejecutar: java MainFumadores

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class MainFumadores {
    static void ejecutar(Runnable... tareas) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(tareas.length);
        try {
            java.util.ArrayList<Future<?>> resultados = new java.util.ArrayList<>();
            for (Runnable tarea : tareas) resultados.add(pool.submit(tarea));
            for (Future<?> resultado : resultados) resultado.get(5, TimeUnit.SECONDS);
        } finally {
            pool.shutdownNow();
            if (!pool.awaitTermination(3, TimeUnit.SECONDS))
                throw new IllegalStateException("Tareas no terminadas");
        }
    }

    public static void main(String[] args) throws Exception {
        MesaIngredientes mesa = new MesaIngredientes();
        ejecutar(new Agente(mesa), new Fumador(mesa, 0), new Fumador(mesa, 1), new Fumador(mesa, 2));
        System.out.println("Turnos completados: " + mesa.total());
    }
}

// Salida esperada:
// Turnos completados: 3
