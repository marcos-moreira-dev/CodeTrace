// Tema:
// Telemetría binaria: marcador 0x7e, longitud fija 4, id y medición short big-endian, checksum de suma módulo 256. El decodificador conserva fragmentos y rechaza longitud y checksum incorrectos; no es autenticación ni protocolo industrial.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 TramaMedicion.java Codificador.java Decodificador.java MainTelemetria.java
// Ejecutar: java MainTelemetria

import java.util.Arrays;

public class MainTelemetria {
    public static void main(String[] args) {
        byte[] trama = Codificador.codificar(new TramaMedicion((short) 3, (short) 25));
        Decodificador receptor = new Decodificador();
        receptor.recibir(Arrays.copyOfRange(trama, 0, 3));
        receptor.recibir(Arrays.copyOfRange(trama, 3, trama.length));
        trama[4] ^= 1;
        receptor.recibir(trama);
    }
}

// Salida esperada:
// 3: 25
// Checksum invalido
