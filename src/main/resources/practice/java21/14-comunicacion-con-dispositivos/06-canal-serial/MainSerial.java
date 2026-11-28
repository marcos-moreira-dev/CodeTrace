// Tema:
// Canal simulado: el emisor agrega delimitador de línea y el receptor acumula fragmentos hasta recibirlo. Un límite evita crecimiento indefinido. Solo se entregan arreglos de bytes.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 CanalBytesSimulado.java Emisor.java Receptor.java MainSerial.java
// Ejecutar: java MainSerial

public class MainSerial {
    public static void main(String[] args) {
        Receptor receptor = new Receptor();
        CanalBytesSimulado.enviar(Emisor.codificar("TEMP25"), receptor);
        CanalBytesSimulado.enviar(Emisor.codificar("OK"), receptor);
    }
}

// Salida esperada:
// TEMP25
// OK
