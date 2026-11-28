// Tema:
// Canal simulado: el emisor agrega delimitador de línea y el receptor acumula fragmentos hasta recibirlo. Un límite evita crecimiento indefinido. Solo se entregan arreglos de bytes.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 CanalBytesSimulado.java Emisor.java Receptor.java MainSerial.java
// Ejecutar: java MainSerial

import java.nio.charset.StandardCharsets;

public class Emisor {
    public static byte[] codificar(String mensaje) {
        if (!mensaje.matches("[A-Z0-9]{1,16}")) throw new IllegalArgumentException("Mensaje");
        return (mensaje + "\n").getBytes(StandardCharsets.US_ASCII);
    }
}

// Salida esperada:
// TEMP25
// OK
