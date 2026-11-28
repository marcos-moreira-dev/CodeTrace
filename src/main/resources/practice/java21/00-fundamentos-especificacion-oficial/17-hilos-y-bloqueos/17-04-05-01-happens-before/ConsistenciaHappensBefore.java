// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §17.4.5; ejemplo 17.4.5-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ConsistenciaHappensBefore.java
// Ejecutar: java ConsistenciaHappensBefore
// Comandos desde la carpeta de la unidad.

public class ConsistenciaHappensBefore {
    static int a, b, r1, r2;
    public static void main(String[] args) throws InterruptedException {
        Thread primero = new Thread(() -> { b = 1; r2 = a; });
        Thread segundo = new Thread(() -> { a = 2; r1 = b; });
        primero.start(); segundo.start();
        primero.join(); segundo.join();
        System.out.println(r2 + " " + r1);
        // 0 0 es permitido por el modelo de memoria, aunque no sea secuencialmente consistente.
    }
}

// Salida esperada:
// Una de las salidas permitidas, sin garantizar cuál:
// 0 0
// 0 1
// 2 0
// 2 1
