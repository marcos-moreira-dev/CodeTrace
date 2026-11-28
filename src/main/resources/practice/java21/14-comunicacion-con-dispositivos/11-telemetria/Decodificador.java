// Tema:
// Telemetría binaria: marcador 0x7e, longitud fija 4, id y medición short big-endian, checksum de suma módulo 256. El decodificador conserva fragmentos y rechaza longitud y checksum incorrectos; no es autenticación ni protocolo industrial.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 TramaMedicion.java Codificador.java Decodificador.java MainTelemetria.java
// Ejecutar: java MainTelemetria

import java.nio.ByteBuffer;

public class Decodificador {
    private final byte[] datos = new byte[7];
    private int usados;

    public void recibir(byte[] fragmento) {
        for (byte b : fragmento) {
            if (usados == 0 && b != 0x7e) continue;
            datos[usados++] = b;
            if (usados == 2 && (datos[1] & 255) != 4) {
                usados = 0;
                System.out.println("Longitud invalida");
            }
            if (usados == datos.length) {
                usados = 0;
                if (Codificador.checksum(datos) != (datos[6] & 255)) {
                    System.out.println("Checksum invalido");
                    continue;
                }
                ByteBuffer carga = ByteBuffer.wrap(datos, 2, 4);
                TramaMedicion trama = new TramaMedicion(carga.getShort(), carga.getShort());
                System.out.println(trama.id() + ": " + trama.valor());
            }
        }
    }
}

// Salida esperada:
// 3: 25
// Checksum invalido
