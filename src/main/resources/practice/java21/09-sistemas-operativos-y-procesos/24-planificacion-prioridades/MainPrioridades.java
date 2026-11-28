// Tema:
// Planificación no expulsiva: un valor mayor indica prioridad mayor. Aging aumenta la prioridad por espera; puede reducir inanición, no garantizar ausencia bajo cualquier carga.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoSimulado.java Prioridades.java Aging.java MainPrioridades.java
// Ejecutar: java MainPrioridades

public class MainPrioridades {
    public static void main(String[] args) throws Exception {
        ProcesoSimulado[] procesos = {
            new ProcesoSimulado("A", 0, 3, 5),
            new ProcesoSimulado("B", 0, 1, 2),
            new ProcesoSimulado("C", 3, 1, 4)
        };
        System.out.print("Fija: ");
        Prioridades.ejecutar(procesos);
        System.out.print("Aging: ");
        Aging.ejecutar(procesos);
    }
}

// Salida esperada:
// Fija: ACB
// Aging: ABC
