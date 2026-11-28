// Tema:
// Las reglas de causalidad impiden justificar valores únicamente mediante un ciclo de lecturas
// y escrituras que depende de esos mismos valores. La ejecución no debe inventar un valor sin
// origen válido.
// Compilar: javac --release 21 -encoding UTF-8 -d . Causalidad.java
// Ejecutar: java Causalidad
// Comandos desde la carpeta de la unidad.

public class Causalidad {
    static int x, y, r1, r2;
    public static void main(String[] args) throws InterruptedException {
        Thread primero = new Thread(() -> {
            r1 = x;
            if (r1 != 0) y = 1;
        });
        Thread segundo = new Thread(() -> {
            r2 = y;
            if (r2 != 0) x = 1;
        });
        primero.start(); segundo.start();
        primero.join(); segundo.join();
        // No pueden aparecer valores inventados por un ciclo de justificación.
        System.out.println(r1 + " " + r2);
    }
}

// Salida esperada:
// 0 0
