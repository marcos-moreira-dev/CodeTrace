// Tema:
// Canal simulado: el emisor agrega delimitador de línea y el receptor acumula fragmentos hasta recibirlo. Un límite evita crecimiento indefinido. Solo se entregan arreglos de bytes.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 CanalBytesSimulado.java Emisor.java Receptor.java MainSerial.java
// Ejecutar: java MainSerial

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class Receptor {
    private final ByteArrayOutputStream pendiente = new ByteArrayOutputStream();
    public void recibir(byte[] fragmento) {
        for (byte b : fragmento) {
            if (b == '\n') {
                System.out.println(pendiente.toString(StandardCharsets.US_ASCII));
                pendiente.reset();
            } else {
                if (pendiente.size() >= 16) {
                    pendiente.reset();
                    throw new IllegalArgumentException("Trama larga");
                }
                pendiente.write(b);
            }
        }
    }
}

// Salida esperada:
// TEMP25
// OK
