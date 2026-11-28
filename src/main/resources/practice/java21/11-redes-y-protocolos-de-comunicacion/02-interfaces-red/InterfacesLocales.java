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
        // La interfaz puede no estar disponible según el entorno de ejecución.
        System.out.println("Interfaz encontrada: " + (interfaz != null));
    }
}

// Salida esperada:
// Loopback: true
// Interfaz encontrada: true
// Alternativa si no existe una interfaz asociada: Interfaz encontrada: false
