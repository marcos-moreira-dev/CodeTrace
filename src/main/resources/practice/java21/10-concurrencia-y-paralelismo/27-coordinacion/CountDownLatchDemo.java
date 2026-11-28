// Tema:
// CountDownLatch permite esperar a que un número fijo de tareas anuncie su finalización. No se reutiliza al llegar a cero.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 CountDownLatchDemo.java
// Ejecutar: java CountDownLatchDemo

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class CountDownLatchDemo {
    static Thread iniciar(Runnable tarea) {
        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true);
        hilo.start();
        return hilo;
    }

    public static void main(String[] args) throws Exception {
        CountDownLatch finalizados = new CountDownLatch(2);
        int[] datos = new int[2];
        Thread a = iniciar(() -> { datos[0] = 4; finalizados.countDown(); });
        Thread b = iniciar(() -> { datos[1] = 6; finalizados.countDown(); });
        if (!finalizados.await(3, TimeUnit.SECONDS)) throw new IllegalStateException("Tiempo agotado");
        System.out.println(datos[0] + datos[1]);
        a.join(1000);
        b.join(1000);
    }
}

// Salida esperada:
// 10
