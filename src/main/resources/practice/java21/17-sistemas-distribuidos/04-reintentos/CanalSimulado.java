// Tema:
// Un fallo transitorio puede justificar un reintento acotado. El reloj simulado registra tiempo de espera por intento y backoff exponencial; no duerme ni realiza I/O. Un timeout no prueba que el servidor no ejecutó la operación.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 CanalSimulado.java ReintentoAcotado.java MainReintento.java
// Ejecutar: java MainReintento

public class CanalSimulado {
    private int fallosRestantes;
    public CanalSimulado(int fallos) { fallosRestantes = fallos; }
    public boolean intentar() {
        if (fallosRestantes > 0) { fallosRestantes--; return false; }
        return true;
    }
}

// Salida esperada:
// Intento 1 en t=0
// Intento 2 en t=3
// Intento 3 en t=7
// Exito: true
// Intento 1 en t=0
// Intento 2 en t=3
// Exito: false
