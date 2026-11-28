// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §17.6; ejemplo 17.6-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ComponentesIndependientes.java
// Ejecutar: java ComponentesIndependientes
// Comandos desde la carpeta de la unidad.

public class ComponentesIndependientes {
    static final byte[] datos = new byte[4];
    static void escribir(int indice) {
        byte esperado = 0;
        for (int i = 0; i < 10_000; i++) {
            if (datos[indice] != esperado) throw new AssertionError("word tearing");
            datos[indice] = ++esperado;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread[] hilos = new Thread[datos.length];
        java.util.concurrent.atomic.AtomicReference<Throwable> fallo =
                new java.util.concurrent.atomic.AtomicReference<>();
        for (int i = 0; i < hilos.length; i++) {
            int indice = i;
            hilos[i] = new Thread(() -> escribir(indice));
            hilos[i].setUncaughtExceptionHandler((h, e) -> fallo.set(e));
            hilos[i].start();
        }
        for (Thread hilo : hilos) hilo.join();
        if (fallo.get() != null) throw new AssertionError(fallo.get());
        System.out.println(java.util.Arrays.toString(datos));
    }
}

// Salida esperada:
// [16, 16, 16, 16]
