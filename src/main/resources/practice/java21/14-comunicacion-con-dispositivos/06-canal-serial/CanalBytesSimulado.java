// Tema:
// Canal simulado: el emisor agrega delimitador de línea y el receptor acumula fragmentos hasta recibirlo. Un límite evita crecimiento indefinido. Solo se entregan arreglos de bytes.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 CanalBytesSimulado.java Emisor.java Receptor.java MainSerial.java
// Ejecutar: java MainSerial

import java.util.Arrays;

public class CanalBytesSimulado {
    public static void enviar(byte[] datos, Receptor receptor) {
        for (int i = 0; i < datos.length; i += 2)
            receptor.recibir(Arrays.copyOfRange(datos, i, Math.min(i + 2, datos.length)));
    }
}

// Salida esperada:
// TEMP25
// OK
