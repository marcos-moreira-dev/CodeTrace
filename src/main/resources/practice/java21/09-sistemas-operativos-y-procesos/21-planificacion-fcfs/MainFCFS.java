// Tema:
// FCFS ejecuta por orden de llegada sin expulsión. El reloj avanza por la duración y salta periodos sin trabajo; espera e intervalo de retorno no son iguales.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoSimulado.java FCFS.java MainFCFS.java
// Ejecutar: java MainFCFS

public class MainFCFS {
    public static void main(String[] args) throws Exception {
        FCFS.ejecutar(new ProcesoSimulado[]{
            new ProcesoSimulado("A", 0, 4),
            new ProcesoSimulado("B", 1, 2),
            new ProcesoSimulado("C", 7, 1)
        });
    }
}

// Salida esperada:
// A espera=0 retorno=4
// B espera=3 retorno=5
// C espera=0 retorno=1
