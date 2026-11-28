// Tema:
// Un padre escribe una solicitud en stdin del hijo y lee la respuesta desde stdout. Cerrar la escritura comunica fin de entrada.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Hijo.java Padre.java
// Ejecutar: java Padre

import java.io.*;

public class Hijo {
    public static void main(String[] args) throws Exception {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(entrada.readLine());
        System.out.println("Respuesta: " + n * 3);
    }
}

// Salida esperada:
// Respuesta: 15
