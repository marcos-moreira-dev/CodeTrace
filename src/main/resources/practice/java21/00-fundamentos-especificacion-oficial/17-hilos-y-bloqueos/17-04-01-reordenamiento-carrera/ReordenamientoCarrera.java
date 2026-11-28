// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §17.4; ejemplo 17.4-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ReordenamientoCarrera.java
// Ejecutar: java ReordenamientoCarrera
// Comandos desde la carpeta de la unidad.

public class ReordenamientoCarrera {
    static int a, b, r1, r2;
    public static void main(String[] args) throws InterruptedException {
        // No hay orden de sincronización entre estas lecturas y escrituras.
        Thread primero = new Thread(() -> { r2 = a; b = 1; });
        Thread segundo = new Thread(() -> { r1 = b; a = 2; });
        primero.start();
        segundo.start();
        primero.join();
        segundo.join();
        System.out.println(r2 + " " + r1);
        // No se garantiza observar el resultado sorprendente 2 1.
    }
}

// Salida esperada:
// Una de las salidas permitidas, sin garantizar cuál:
// 0 0
// 0 1
// 2 0
// 2 1
