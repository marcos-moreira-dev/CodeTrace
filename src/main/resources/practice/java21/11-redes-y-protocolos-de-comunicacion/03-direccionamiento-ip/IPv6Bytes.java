// Tema:
// IPv6 ocupa dieciséis bytes. La notación textual comprimida no cambia la cantidad de bits.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 IPv6Bytes.java
// Ejecutar: java IPv6Bytes

import java.net.*;

public class IPv6Bytes {
    public static void main(String[] args) throws Exception {
        byte[] datos = InetAddress.getByName("2001:db8::1").getAddress();
        System.out.println("Bits: " + datos.length * 8);
        System.out.printf("Prefijo: %02x%02x:%02x%02x%n", datos[0] & 255, datos[1] & 255, datos[2] & 255, datos[3] & 255);
        System.out.println("Ultimo octeto: " + (datos[15] & 255));
    }
}

// Salida esperada:
// Bits: 128
// Prefijo: 2001:0db8
// Ultimo octeto: 1
