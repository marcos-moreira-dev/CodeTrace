// Tema:
// Un fallo transitorio puede justificar un reintento acotado. El reloj simulado registra tiempo de espera por intento y backoff exponencial; no duerme ni realiza I/O. Un timeout no prueba que el servidor no ejecutó la operación.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 CanalSimulado.java ReintentoAcotado.java MainReintento.java
// Ejecutar: java MainReintento

public class ReintentoAcotado {
    public static boolean enviar(CanalSimulado canal, int maximo) {
        if (maximo < 1 || maximo > 8) throw new IllegalArgumentException("Intentos");
        int reloj = 0, espera = 1;
        for (int intento = 1; intento <= maximo; intento++) {
            System.out.println("Intento " + intento + " en t=" + reloj);
            if (canal.intentar()) return true;
            reloj += 2; // timeout simulado
            if (intento < maximo) { reloj += espera; espera *= 2; }
        }
        return false;
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
