// Tema:
// NetworkInterface representa una interfaz del equipo. Consultamos únicamente la existencia de una interfaz loopback, sin divulgar direcciones privadas ni recorrer otros equipos.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 InterfacesLocales.java
// Ejecutar: java InterfacesLocales

import java.net.*;

public class InterfacesLocales {
    public static void main(String[] args) throws Exception {
        InetAddress local = InetAddress.getLoopbackAddress();
        NetworkInterface interfaz = NetworkInterface.getByInetAddress(local);
        System.out.println("Loopback: " + local.isLoopbackAddress());
        System.out.println("Consulta local completada: true");
    }
}

// Salida esperada:
// Loopback: true
// Consulta local completada: true
