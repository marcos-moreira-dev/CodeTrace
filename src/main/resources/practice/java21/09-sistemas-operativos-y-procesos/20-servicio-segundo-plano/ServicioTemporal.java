// Tema:
// Servicio temporal: un proceso atiende una petición y termina con una orden explícita. No instala un daemon ni modifica servicios del sistema.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 ServicioTemporal.java MainServicio.java
// Ejecutar: java MainServicio

import java.io.*;

public class ServicioTemporal {
    public static void main(String[] args) throws Exception {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String linea;
        while ((linea = entrada.readLine()) != null) {
            if (linea.equals("salir")) break;
            if (linea.equals("estado")) System.out.println("Servicio activo");
        }
        System.out.println("Servicio cerrado");
    }
}

// Salida esperada:
// Servicio activo
// Servicio cerrado
