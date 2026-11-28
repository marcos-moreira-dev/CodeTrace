// Tema:
// Round Robin asigna un quantum máximo por turno y devuelve procesos incompletos a la cola. Modelo acotado con todos los trabajos listos en t=0; no representa el planificador real.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoSimulado.java RoundRobin.java MainRoundRobin.java
// Ejecutar: java MainRoundRobin

import java.util.ArrayDeque;

public class RoundRobin {
    public static void ejecutar(ProcesoSimulado[] p, int quantum) {
        if (quantum <= 0) throw new IllegalArgumentException("Quantum");
        int[] restante = new int[p.length];
        ArrayDeque<Integer> listos = new ArrayDeque<>();
        // Todos los procesos del ejemplo llegan en el instante cero.
        for (int i = 0; i < p.length; i++) {
            if (p[i].llegada() != 0 || p[i].duracion() <= 0)
                throw new IllegalArgumentException("Modelo de llegada cero");
            restante[i] = p[i].duracion();
            listos.addLast(i);
        }
        while (!listos.isEmpty()) {
            int i = listos.removeFirst();
            int turno = Math.min(quantum, restante[i]);
            for (int t = 0; t < turno; t++) System.out.print(p[i].id());
            restante[i] -= turno;
            if (restante[i] > 0) listos.addLast(i);
        }
        System.out.println();
    }
}

// Salida esperada:
// AABBCAABA
