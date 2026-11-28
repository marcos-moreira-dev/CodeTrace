// Tema:
// TCP proporciona un flujo fiable de bytes, no mensajes. El ejemplo usa EOF mediante shutdownOutput para delimitar una única petición; el servidor devuelve los bytes y cierra la conexión. Solo loopback y puerto efímero.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainEcho.java
// Ejecutar: java MainEcho

import java.util.concurrent.*;

public class MainEcho {
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
// Hola TCP
