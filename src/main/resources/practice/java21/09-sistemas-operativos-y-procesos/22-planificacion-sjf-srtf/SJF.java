// Tema:
// SJF elige el trabajo listo más corto y lo ejecuta completo; SRTF reevalúa el tiempo restante cada unidad y puede expulsar. Duraciones enteras positivas y llegadas no negativas; empate por orden del arreglo.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoSimulado.java SJF.java SRTF.java MainPlanificacion.java
// Ejecutar: java MainPlanificacion

public class SJF {
    public static void ejecutar(ProcesoSimulado[] p) {
        boolean[] terminado = new boolean[p.length];
        int reloj = 0, pendientes = p.length;
        while (pendientes > 0) {
            int mejor = -1;
            for (int i = 0; i < p.length; i++) {
                if (!terminado[i] && p[i].llegada() <= reloj
                        && (mejor == -1 || p[i].duracion() < p[mejor].duracion())) mejor = i;
            }
            if (mejor == -1) { reloj++; continue; }
            // Una vez elegido, el proceso termina sin ser expulsado.
            for (int t = 0; t < p[mejor].duracion(); t++) System.out.print(p[mejor].id());
            reloj += p[mejor].duracion();
            terminado[mejor] = true;
            pendientes--;
        }
        System.out.println();
    }
}

// Salida esperada:
// SJF: AAAACBB
// SRTF: ABBCAAA
