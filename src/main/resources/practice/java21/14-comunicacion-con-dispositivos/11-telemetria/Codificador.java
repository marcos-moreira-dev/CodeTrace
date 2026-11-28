// Tema:
// Telemetría binaria: marcador 0x7e, longitud fija 4, id y medición short big-endian, checksum de suma módulo 256. El decodificador conserva fragmentos y rechaza longitud y checksum incorrectos; no es autenticación ni protocolo industrial.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 TramaMedicion.java Codificador.java Decodificador.java MainTelemetria.java
// Ejecutar: java MainTelemetria

import java.nio.ByteBuffer;

public class Codificador {
    public static byte[] codificar(TramaMedicion trama) {
        ByteBuffer b = ByteBuffer.allocate(7);
        b.put((byte) 0x7e).put((byte) 4).putShort(trama.id()).putShort(trama.valor());
        b.put((byte) checksum(b.array()));
        return b.array();
    }
    public static int checksum(byte[] b) {
        int suma = 0;
        for (int i = 1; i < 6; i++) suma += b[i] & 255;
        return suma & 255;
    }
}

// Salida esperada:
// 3: 25
// Checksum invalido
