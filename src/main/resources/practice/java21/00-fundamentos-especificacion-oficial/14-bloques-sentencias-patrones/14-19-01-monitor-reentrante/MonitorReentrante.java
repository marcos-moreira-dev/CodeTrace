// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §14.19; ejemplo 14.19-1
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
