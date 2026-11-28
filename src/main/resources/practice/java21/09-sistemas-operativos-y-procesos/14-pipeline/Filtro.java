// Tema:
// Una tubería conecta la salida de cada proceso con la entrada del siguiente. El generador emite números, el filtro conserva pares y el consumidor suma.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Generador.java Filtro.java Consumidor.java MainPipeline.java
// Ejecutar: java MainPipeline

import java.io.*;

public class Filtro {
    public static void main(String[] args) throws Exception {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String linea;
        while ((linea = entrada.readLine()) != null) {
            int n = Integer.parseInt(linea);
            if (n % 2 == 0) System.out.println(n);
        }
    }
}

// Salida esperada:
// Suma: 6
