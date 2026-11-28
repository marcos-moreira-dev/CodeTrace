// Tema:
// La redirección conecta los flujos estándar de un hijo con archivos. Se usan exclusivamente archivos temporales propios.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Hijo.java Padre.java
// Ejecutar: java Padre

import java.io.*;

public class Hijo {
    public static void main(String[] args) throws Exception {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(entrada.readLine());
        System.out.println(n * 2);
        System.err.println("Procesado");
    }
}

// Salida esperada:
// 16
// Procesado
