// Tema:
// Barbero dormilón: una cola representa las sillas de espera. offer rechaza si no hay sitio; take espera si no hay clientes. Se llena la sala antes de iniciar el servicio para que el rechazo sea reproducible; un centinela cierra la barbería.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Barberia.java Barbero.java Cliente.java MainBarberia.java
// Ejecutar: java MainBarberia

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class MainBarberia {
    public static void main(String[] args) throws Exception {
        Barberia barberia = new Barberia();
        for (int i = 1; i <= 3; i++)
            System.out.println("Cliente " + i + ": " + new Cliente(i).llegar(barberia));
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            Future<?> barbero = pool.submit(new Barbero(barberia));
            barberia.terminarServicio();
            barbero.get(4, TimeUnit.SECONDS);
            System.out.println("Atendidos: " + barberia.atendidos());
        } finally { pool.shutdownNow(); }
    }
}

// Salida esperada:
// Cliente 1: true
// Cliente 2: true
// Cliente 3: false
// Atendidos: 2
