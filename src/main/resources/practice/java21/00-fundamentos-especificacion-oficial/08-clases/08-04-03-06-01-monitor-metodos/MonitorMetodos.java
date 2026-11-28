// Tema:
// Un método synchronized de instancia adquiere el monitor de this. Un método static
// synchronized adquiere el monitor del objeto Class correspondiente.
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
