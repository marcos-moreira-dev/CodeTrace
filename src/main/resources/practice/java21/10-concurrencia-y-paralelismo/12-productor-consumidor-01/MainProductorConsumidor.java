// Tema:
// Buffer circular de capacidad fija: libres cuenta huecos, ocupados cuenta datos, mutex protege índices. Si se interrumpe la espera del mutex se devuelve el permiso reservado; un centinela termina al consumidor.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 BufferCircular.java Productor.java Consumidor.java MainProductorConsumidor.java
// Ejecutar: java MainProductorConsumidor

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class MainProductorConsumidor {
    static void ejecutar(Runnable... tareas) throws Exception {
        try (ExecutorService pool = Executors.newFixedThreadPool(tareas.length)) {
            java.util.ArrayList<Future<?>> resultados = new java.util.ArrayList<>();
            try {
                for (Runnable tarea : tareas) resultados.add(pool.submit(tarea));
                for (Future<?> resultado : resultados) resultado.get(5, TimeUnit.SECONDS);
            } finally { pool.shutdownNow(); }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferCircular buffer = new BufferCircular(2);
        ejecutar(new Productor(buffer), new Consumidor(buffer));
    }
}

// Salida esperada:
// Suma: 10
