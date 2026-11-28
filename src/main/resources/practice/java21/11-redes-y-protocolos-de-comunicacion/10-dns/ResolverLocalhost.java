// Tema:
// InetAddress resuelve nombres por el mecanismo configurado en el sistema. localhost evita depender de un dominio externo; no se presupone que la resolución use una consulta DNS de red.
// Modalidad: modelo y ejecución.
// Compilar: javac --release 21 -encoding UTF-8 ResolverLocalhost.java
// Ejecutar: java ResolverLocalhost

import java.net.*;

public class ResolverLocalhost {
    public static void main(String[] args) throws Exception {
        InetAddress direccion = InetAddress.getByName("localhost");
        System.out.println("Resuelto: " + (direccion.getAddress().length == 4 || direccion.getAddress().length == 16));
    }
}

// Salida esperada:
// Resuelto: true
