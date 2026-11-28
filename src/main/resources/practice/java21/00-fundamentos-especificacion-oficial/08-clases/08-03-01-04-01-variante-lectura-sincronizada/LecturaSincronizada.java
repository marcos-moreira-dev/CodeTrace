// Tema:
// Si la escritura y la lectura de dos campos usan el mismo monitor, ninguna lectura puede
// intercalarse con una actualización parcial. A diferencia de volatile por separado,
// synchronized protege la operación compuesta.
// Compilar: javac --release 21 -encoding UTF-8 -d . LecturaSincronizada.java
// Ejecutar: java LecturaSincronizada

public class LecturaSincronizada {
    static int primero, segundo;
    static synchronized void escribir() { primero++; segundo++; }
    static synchronized boolean iguales() { return primero == segundo; }
    public static void main(String[] args) throws InterruptedException {
        Thread escritor = new Thread(() -> {
            for (int n = 0; n < 1000; n++) escribir();
        });
        escritor.start();
        boolean siempre = true;
        for (int n = 0; n < 1000; n++) siempre &= iguales();
        escritor.join();
        System.out.println(siempre);
        System.out.println(primero + " " + segundo);
    }
}

// Salida esperada:
// true
// 1000 1000
