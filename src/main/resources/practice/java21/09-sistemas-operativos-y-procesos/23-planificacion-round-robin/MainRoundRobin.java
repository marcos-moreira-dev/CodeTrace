// Tema:
// Round Robin asigna un quantum máximo por turno y devuelve procesos incompletos a la cola. Modelo acotado con todos los trabajos listos en t=0; no representa el planificador real.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoSimulado.java RoundRobin.java MainRoundRobin.java
// Ejecutar: java MainRoundRobin

public class MainRoundRobin {
    public static void main(String[] args) throws Exception {
        RoundRobin.ejecutar(new ProcesoSimulado[]{
            new ProcesoSimulado("A", 0, 5),
            new ProcesoSimulado("B", 0, 3),
            new ProcesoSimulado("C", 0, 1)
        }, 2);
    }
}

// Salida esperada:
// AABBCAABA
