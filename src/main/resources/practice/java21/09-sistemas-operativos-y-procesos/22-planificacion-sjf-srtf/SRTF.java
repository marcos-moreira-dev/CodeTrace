// Tema:
// SJF elige el trabajo listo más corto y lo ejecuta completo; SRTF reevalúa el tiempo restante cada unidad y puede expulsar. Duraciones enteras positivas y llegadas no negativas; empate por orden del arreglo.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoSimulado.java SJF.java SRTF.java MainPlanificacion.java
// Ejecutar: java MainPlanificacion

public class SRTF {
    public static void ejecutar(ProcesoSimulado[] p) {
        int[] restante = new int[p.length];
        for (int i = 0; i < p.length; i++) restante[i] = p[i].duracion();
        int reloj = 0, pendientes = p.length;
        while (pendientes > 0) {
            int mejor = -1;
            for (int i = 0; i < p.length; i++) {
                if (restante[i] > 0 && p[i].llegada() <= reloj
                        && (mejor == -1 || restante[i] < restante[mejor])) mejor = i;
            }
            if (mejor != -1) {
                // Volver a seleccionar cada unidad de tiempo permite expulsión.
                System.out.print(p[mejor].id());
                if (--restante[mejor] == 0) pendientes--;
            }
            reloj++;
        }
        System.out.println();
    }
}

// Salida esperada:
// SJF: AAAACBB
// SRTF: ABBCAAA
