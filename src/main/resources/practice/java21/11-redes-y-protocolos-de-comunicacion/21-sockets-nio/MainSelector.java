// Tema:
// Un Selector multiplica canales no bloqueantes sobre un hilo. Una clave cambia de OP_READ a OP_WRITE cuando se han acumulado cuatro bytes; el buffer conserva la posición en operaciones parciales. Se limita a una conexión y a un plazo fijo.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ServidorSelector.java Cliente.java MainSelector.java
// Ejecutar: java MainSelector

import java.util.concurrent.*;

public class MainSelector {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try (ServidorSelector servidor = new ServidorSelector()) {
            int puerto = servidor.puerto();
            Future<?> trabajo = pool.submit(() -> { servidor.atender(); return null; });
            System.out.println(Cliente.solicitar(puerto));
            trabajo.get(6, TimeUnit.SECONDS);
        } finally { pool.shutdownNow(); }
    }
}

// Salida esperada:
// HOLA
