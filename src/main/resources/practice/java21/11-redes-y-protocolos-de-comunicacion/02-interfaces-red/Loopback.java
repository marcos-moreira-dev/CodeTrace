// Tema:
// Una dirección loopback identifica al propio equipo; el tráfico no necesita salir hacia la red física. Java puede elegir IPv4 o IPv6.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Loopback.java
// Ejecutar: java Loopback

import java.net.*;

public class Loopback {
    public static void main(String[] args) throws Exception {
        InetAddress local = InetAddress.getLoopbackAddress();
        System.out.println("Es local: " + local.isLoopbackAddress());
        int longitud = local.getAddress().length;
        System.out.println("Formato IP valido: " + (longitud == 4 || longitud == 16));
    }
}

// Salida esperada:
// Es local: true
// Formato IP valido: true
