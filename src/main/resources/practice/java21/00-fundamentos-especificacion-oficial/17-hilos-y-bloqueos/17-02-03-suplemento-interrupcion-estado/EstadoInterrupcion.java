// Tema:
// interrupt establece el estado de interrupción de un hilo; isInterrupted lo consulta sin
// borrarlo. Thread.interrupted consulta y borra el estado del hilo que realiza la llamada.
// Interrumpir no equivale a terminar forzosamente el hilo.
// Compilar: javac --release 21 -encoding UTF-8 -d . EstadoInterrupcion.java
// Ejecutar: java EstadoInterrupcion
// Comandos desde la carpeta de la unidad.

public class EstadoInterrupcion {
    public static void main(String[] args) {
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().isInterrupted());
        System.out.println(Thread.interrupted());
        System.out.println(Thread.currentThread().isInterrupted());
    }
}

// Salida esperada:
// true
// true
// false
