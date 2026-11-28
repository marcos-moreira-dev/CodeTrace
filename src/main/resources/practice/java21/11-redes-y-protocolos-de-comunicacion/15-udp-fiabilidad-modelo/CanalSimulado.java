// Tema:
// Stop-and-wait: se envía una trama, se espera ACK y se retransmite si se pierde. El receptor distingue duplicados por número de secuencia. Modelo determinista: se pierde el primer ACK, no se implementa temporizador de red real.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 CanalSimulado.java StopAndWait.java MainStopAndWait.java
// Ejecutar: java MainStopAndWait

public class CanalSimulado {
    private int esperado, entregas, intentos;
    public boolean transmitir(int secuencia) {
        intentos++;
        if (secuencia == esperado) {
            entregas++;
            esperado = 1 - esperado;
        }
        return intentos != 1; // Primer ACK perdido.
    }
    public int entregas() { return entregas; }
}

// Salida esperada:
// Intentos: 2
// Entregas: 1
