// Tema:
// Planificación no expulsiva: un valor mayor indica prioridad mayor. Aging aumenta la prioridad por espera; puede reducir inanición, no garantizar ausencia bajo cualquier carga.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoSimulado.java Prioridades.java Aging.java MainPrioridades.java
// Ejecutar: java MainPrioridades

public class Prioridades {
    public static void ejecutar(ProcesoSimulado[] p) {
        boolean[] terminado = new boolean[p.length];
        int reloj = 0, pendientes = p.length;
        while (pendientes > 0) {
            int mejor = -1, valor = Integer.MIN_VALUE;
            for (int i = 0; i < p.length; i++) {
                if (terminado[i] || p[i].llegada() > reloj) continue;
                int efectiva = p[i].prioridad();
                if (mejor == -1 || efectiva > valor) {
                    mejor = i;
                    valor = efectiva;
                }
            }
            if (mejor == -1) { reloj++; continue; }
            System.out.print(p[mejor].id());
            reloj += p[mejor].duracion();
            terminado[mejor] = true;
            pendientes--;
        }
        System.out.println();
    }
}

// Salida esperada:
// Fija: ACB
// Aging: ABC
