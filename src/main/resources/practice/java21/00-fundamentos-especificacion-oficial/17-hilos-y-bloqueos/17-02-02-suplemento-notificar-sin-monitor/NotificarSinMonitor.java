// Tema:
// notify y notifyAll requieren que el hilo posea el monitor del objeto. Llamar a notifyAll
// sin synchronized provoca IllegalMonitorStateException incluso si ningún hilo está
// esperando.
// Compilar: javac --release 21 -encoding UTF-8 -d . NotificarSinMonitor.java
// Ejecutar: java NotificarSinMonitor
// Comandos desde la carpeta de la unidad.

public class NotificarSinMonitor {
    public static void main(String[] args) {
        Object monitor = new Object();
        try {
            monitor.notifyAll();
        } catch (IllegalMonitorStateException e) {
            System.out.println("monitor no adquirido");
        }
    }
}

// Salida esperada:
// monitor no adquirido
