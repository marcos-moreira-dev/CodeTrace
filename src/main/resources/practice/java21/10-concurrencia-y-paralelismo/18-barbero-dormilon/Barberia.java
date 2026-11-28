// Tema:
// Barbero dormilón: una cola representa las sillas de espera. offer rechaza si no hay sitio; take espera si no hay clientes. Se llena la sala antes de iniciar el servicio para que el rechazo sea reproducible; un centinela cierra la barbería.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Barberia.java Barbero.java Cliente.java MainBarberia.java
// Ejecutar: java MainBarberia

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Barberia {
    private final ArrayBlockingQueue<Integer> sillas = new ArrayBlockingQueue<>(2);
    private final AtomicInteger atendidos = new AtomicInteger();
    public boolean entrar(int cliente) { return sillas.offer(cliente); }
    public int siguiente() throws InterruptedException { return sillas.take(); }
    public void terminarServicio() throws InterruptedException { sillas.put(-1); }
    public void cortar() { atendidos.incrementAndGet(); }
    public int atendidos() { return atendidos.get(); }
}

// Salida esperada:
// Cliente 1: true
// Cliente 2: true
// Cliente 3: false
// Atendidos: 2
