// Tema:
// volatile proporciona garantías de visibilidad y orden entre accesos a ese campo. No
// convierte operaciones compuestas como incremento en operaciones atómicas ni agrupa dos
// campos en una transacción.
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
