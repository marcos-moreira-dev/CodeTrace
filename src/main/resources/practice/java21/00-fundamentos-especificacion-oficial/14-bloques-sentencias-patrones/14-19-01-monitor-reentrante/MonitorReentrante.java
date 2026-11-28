// Tema:
// Un monitor Java es reentrante: el mismo hilo puede adquirirlo varias veces. Cada salida del
// bloque synchronized libera una adquisición.
// Compilar: javac --release 21 -encoding UTF-8 -d . MonitorReentrante.java
// Ejecutar: java MonitorReentrante
// Comandos desde la carpeta de la unidad.

public class MonitorReentrante {
    public static void main(String[] args) {
        Object monitor = new Object();
        synchronized (monitor) {
            synchronized (monitor) {
                System.out.println("dos bloqueos del mismo hilo");
            }
        }
    }
}

// Salida esperada:
// dos bloqueos del mismo hilo
