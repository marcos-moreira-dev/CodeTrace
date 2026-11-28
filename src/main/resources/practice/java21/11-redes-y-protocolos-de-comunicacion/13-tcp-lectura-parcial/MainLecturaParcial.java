// Tema:
// read no garantiza completar el buffer. Se acumulan bytes hasta completar cuatro octetos; EOF prematuro es truncamiento. El cliente manda tres bytes y cierra la escritura para provocar el caso controlado.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainLecturaParcial.java
// Ejecutar: java MainLecturaParcial

import java.util.concurrent.*;

public class MainLecturaParcial {
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
// Truncado: 3
