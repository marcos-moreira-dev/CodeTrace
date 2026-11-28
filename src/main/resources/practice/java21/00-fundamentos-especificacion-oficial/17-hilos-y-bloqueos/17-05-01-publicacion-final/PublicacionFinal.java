// Tema:
// Un campo final de un objeto correctamente construido tiene garantías especiales de
// visibilidad si otro hilo llega a observar la referencia. Otros campos no disfrutan
// automáticamente de esa garantía.
// Compilar: javac --release 21 -encoding UTF-8 -d . DatoPublicado.java PublicacionFinal.java
// Ejecutar: java PublicacionFinal
// Comandos desde la carpeta de la unidad.

public class PublicacionFinal {
    static DatoPublicado dato;
    public static void main(String[] args) throws InterruptedException {
        Thread escritor = new Thread(() -> dato = new DatoPublicado());
        Thread lector = new Thread(() -> {
            DatoPublicado observado = dato;
            if (observado == null) System.out.println("sin referencia");
            else System.out.println(observado.fijo + " " + observado.normal);
        });
        escritor.start(); lector.start();
        escritor.join(); lector.join();
        // Si el lector observa el objeto, fijo debe valer 7.
    }
}

// Salida esperada:
// Una de las salidas permitidas, sin garantizar cuál:
// sin referencia
// 7 0
// 7 9
