// Tema:
// wait exige poseer el monitor y lo libera durante la espera. Al despertar, el hilo vuelve
// a adquirirlo y comprueba la condición en un while, porque una notificación no garantiza
// por sí sola que la condición sea verdadera.
// Compilar: javac --release 21 -encoding UTF-8 -d . EsperaMonitor.java
// Ejecutar: java EsperaMonitor
// Comandos desde la carpeta de la unidad.

public class EsperaMonitor {
    static final Object monitor = new Object();
    static boolean listo;

    public static void main(String[] args) throws InterruptedException {
        Thread lector = new Thread(() -> {
            synchronized (monitor) {
                try {
                    while (!listo) {
                        monitor.wait();
                    }
                    System.out.println("listo");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        lector.start();
        synchronized (monitor) {
            listo = true;
            monitor.notifyAll();
        }
        lector.join();
    }
}

// Salida esperada:
// listo
