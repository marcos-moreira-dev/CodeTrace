// Tema:
// SJF elige el trabajo listo más corto y lo ejecuta completo; SRTF reevalúa el tiempo restante cada unidad y puede expulsar. Duraciones enteras positivas y llegadas no negativas; empate por orden del arreglo.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoSimulado.java SJF.java SRTF.java MainPlanificacion.java
// Ejecutar: java MainPlanificacion

public class MainPlanificacion {
    public static void main(String[] args) throws Exception {
        ProcesoSimulado[] procesos = {
            new ProcesoSimulado("A", 0, 4),
            new ProcesoSimulado("B", 1, 2),
            new ProcesoSimulado("C", 2, 1)
        };
        System.out.print("SJF: ");
        SJF.ejecutar(procesos);
        System.out.print("SRTF: ");
        SRTF.ejecutar(procesos);
    }
}

// Salida esperada:
// SJF: AAAACBB
// SRTF: ABBCAAA
