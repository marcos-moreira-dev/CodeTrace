// Tema:
// Un fallo transitorio puede justificar un reintento acotado. El reloj simulado registra tiempo de espera por intento y backoff exponencial; no duerme ni realiza I/O. Un timeout no prueba que el servidor no ejecutó la operación.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 CanalSimulado.java ReintentoAcotado.java MainReintento.java
// Ejecutar: java MainReintento

public class MainReintento {
    public static void main(String[] args) {
        System.out.println("Exito: " + ReintentoAcotado.enviar(new CanalSimulado(2), 3));
        System.out.println("Exito: " + ReintentoAcotado.enviar(new CanalSimulado(5), 2));
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
