// Tema:
// Interrumpir un hilo solicita cooperación. Una operación bloqueante puede lanzar InterruptedException; el hilo decide cómo terminar.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 CancelacionCooperativa.java
// Ejecutar: java CancelacionCooperativa

import java.util.concurrent.*;

public class CancelacionCooperativa {
    public static void main(String[] args) throws Exception {
        CountDownLatch iniciado = new CountDownLatch(1);
        Thread trabajador = new Thread(() -> {
            iniciado.countDown();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Cancelacion recibida");
            }
        });
        trabajador.start();
        try {
            if (!iniciado.await(3, TimeUnit.SECONDS)) throw new IllegalStateException("No inicio");
            trabajador.interrupt();
            trabajador.join(3000);
            System.out.println("Vivo: " + trabajador.isAlive());
        } finally {
            trabajador.interrupt();
        }
    }
}

// Salida esperada:
// Cancelacion recibida
// Vivo: false
