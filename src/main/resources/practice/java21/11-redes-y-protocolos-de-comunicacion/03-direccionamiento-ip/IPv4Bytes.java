// Tema:
// IPv4 utiliza cuatro octetos. En Java byte es con signo; & 0xff recupera el valor sin signo.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 IPv4Bytes.java
// Ejecutar: java IPv4Bytes

import java.net.*;

public class IPv4Bytes {
    public static void main(String[] args) throws Exception {
        byte[] datos = InetAddress.getByName("192.0.2.17").getAddress();
        System.out.println("Bits: " + datos.length * 8);
        for (int i = 0; i < datos.length; i++) {
            if (i > 0) System.out.print(".");
            System.out.print(datos[i] & 0xff);
        }
        System.out.println();
    }
}

// Salida esperada:
// Bits: 32
// 192.0.2.17
