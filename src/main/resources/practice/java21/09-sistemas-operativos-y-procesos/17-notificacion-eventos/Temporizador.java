// Tema:
// ScheduledExecutorService programa una tarea para más adelante. Es un mecanismo de Java, no una señal UNIX. El futuro permite esperar y acotar la ejecución.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Temporizador.java
// Ejecutar: java Temporizador

import java.util.concurrent.*;

public class Temporizador {
    public static void main(String[] args) throws Exception {
        ScheduledExecutorService servicio = Executors.newSingleThreadScheduledExecutor();
        try {
            Future<String> tarea = servicio.schedule(() -> "Evento recibido", 20, TimeUnit.MILLISECONDS);
            System.out.println(tarea.get(3, TimeUnit.SECONDS));
        } finally {
            servicio.shutdownNow();
        }
    }
}

// Salida esperada:
// Evento recibido
