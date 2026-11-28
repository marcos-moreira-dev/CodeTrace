// Tema:
// Stop-and-wait: se envía una trama, se espera ACK y se retransmite si se pierde. El receptor distingue duplicados por número de secuencia. Modelo determinista: se pierde el primer ACK, no se implementa temporizador de red real.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 CanalSimulado.java StopAndWait.java MainStopAndWait.java
// Ejecutar: java MainStopAndWait

public class StopAndWait {
    public static int enviar(CanalSimulado canal, int secuencia) {
        for (int intento = 1; intento <= 3; intento++)
            if (canal.transmitir(secuencia)) return intento;
        throw new IllegalStateException("Sin ACK");
    }
}

// Salida esperada:
// Intentos: 2
// Entregas: 1
