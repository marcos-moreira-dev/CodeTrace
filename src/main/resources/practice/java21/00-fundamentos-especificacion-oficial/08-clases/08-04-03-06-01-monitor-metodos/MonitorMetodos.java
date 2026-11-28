// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.3.6; ejemplo 8.4.3.6-1
// Compilar: javac --release 21 -encoding UTF-8 -d . MonitorMetodos.java
// Ejecutar: java MonitorMetodos
// Comandos desde la carpeta de la unidad.

public class MonitorMetodos {
    synchronized boolean instancia() { return Thread.holdsLock(this); }
    static synchronized boolean clase() { return Thread.holdsLock(MonitorMetodos.class); }
    public static void main(String[] args) {
        System.out.println(new MonitorMetodos().instancia());
        System.out.println(clase());
    }
}

// Salida esperada:
// true
// true
