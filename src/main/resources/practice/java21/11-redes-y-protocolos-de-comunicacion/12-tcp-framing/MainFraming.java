// Tema:
// Framing: un entero de cuatro bytes anuncia la longitud de la carga UTF-8. readFully reconstruye el mensaje aunque TCP entregue fragmentos; la longitud se valida antes de reservar memoria.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 CodificadorTrama.java Servidor.java Cliente.java MainFraming.java
// Ejecutar: java MainFraming

import java.util.concurrent.*;

public class MainFraming {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try (Servidor servidor = new Servidor()) {
            Future<?> trabajo = pool.submit(() -> { servidor.atender(); return null; });
            System.out.println(Cliente.solicitar(servidor.puerto()));
            trabajo.get(5, TimeUnit.SECONDS);
        } finally { pool.shutdownNow(); }
    }
}

// Salida esperada:
// OK pan
// OK leche
