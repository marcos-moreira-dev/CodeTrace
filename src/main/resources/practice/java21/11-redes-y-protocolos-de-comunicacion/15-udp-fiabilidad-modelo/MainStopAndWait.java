// Tema:
// Stop-and-wait: se envía una trama, se espera ACK y se retransmite si se pierde. El receptor distingue duplicados por número de secuencia. Modelo determinista: se pierde el primer ACK, no se implementa temporizador de red real.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 CanalSimulado.java StopAndWait.java MainStopAndWait.java
// Ejecutar: java MainStopAndWait

public class MainStopAndWait {
    public static void main(String[] args) throws Exception {
        CanalSimulado canal = new CanalSimulado();
        System.out.println("Intentos: " + StopAndWait.enviar(canal, 0));
        System.out.println("Entregas: " + canal.entregas());
    }
}

// Salida esperada:
// Intentos: 2
// Entregas: 1
