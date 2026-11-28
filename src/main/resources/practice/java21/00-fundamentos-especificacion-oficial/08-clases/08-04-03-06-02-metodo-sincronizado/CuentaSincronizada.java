// Tema:
// La sincronización de instancia impide que dos hilos ejecuten simultáneamente la sección
// protegida sobre el mismo objeto. Así se protege el incremento compuesto.
// Compilar: javac --release 21 -encoding UTF-8 -d . CuentaSincronizada.java
// Ejecutar: java CuentaSincronizada
// Comandos desde la carpeta de la unidad.

public class CuentaSincronizada {
    int valor;
    synchronized void sumar() { valor++; }
    public static void main(String[] args) throws InterruptedException {
        CuentaSincronizada c = new CuentaSincronizada();
        Runnable trabajo = () -> { for (int i = 0; i < 1000; i++) c.sumar(); };
        Thread a = new Thread(trabajo), b = new Thread(trabajo);
        a.start(); b.start(); a.join(); b.join();
        System.out.println(c.valor);
    }
}

// Salida esperada:
// 2000
