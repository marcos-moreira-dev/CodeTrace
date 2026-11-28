// Tema:
// FCFS ejecuta por orden de llegada sin expulsión. El reloj avanza por la duración y salta periodos sin trabajo; espera e intervalo de retorno no son iguales.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoSimulado.java FCFS.java MainFCFS.java
// Ejecutar: java MainFCFS

public class FCFS {
    public static void ejecutar(ProcesoSimulado[] procesos) {
        // Ordenar por llegada preservando el orden original en empates.
        ProcesoSimulado[] cola = procesos.clone();
        for (int i = 1; i < cola.length; i++) {
            ProcesoSimulado p = cola[i];
            int j = i - 1;
            while (j >= 0 && cola[j].llegada() > p.llegada()) {
                cola[j + 1] = cola[j--];
            }
            cola[j + 1] = p;
        }
        int reloj = 0;
        for (ProcesoSimulado p : cola) {
            int inicio = Math.max(reloj, p.llegada());
            reloj = inicio + p.duracion();
            System.out.println(p.id() + " espera=" + (inicio - p.llegada())
                    + " retorno=" + (reloj - p.llegada()));
        }
    }
}

// Salida esperada:
// A espera=0 retorno=4
// B espera=3 retorno=5
// C espera=0 retorno=1
