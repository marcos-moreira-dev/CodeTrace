// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.3.1.4; ejemplo 8.3.1.4-1
// Compilar: javac --release 21 -encoding UTF-8 -d . CamposVolatile.java
// Ejecutar: java CamposVolatile
// Comandos desde la carpeta de la unidad.

public class CamposVolatile {
    static volatile int primero, segundo;
    static void escribir() { primero++; segundo++; }
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 100; i++) escribir();
        });
        t.start();
        t.join();
        // volatile no convierte las dos actualizaciones en una operación atómica.
        System.out.println(primero + " " + segundo);
    }
}

// Salida esperada:
// 100 100
