// Tema:
// Una tubería conecta la salida de cada proceso con la entrada del siguiente. El generador emite números, el filtro conserva pares y el consumidor suma.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Generador.java Filtro.java Consumidor.java MainPipeline.java
// Ejecutar: java MainPipeline

import java.io.*;

public class Consumidor {
    public static void main(String[] args) throws Exception {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        int total = 0;
        String linea;
        while ((linea = entrada.readLine()) != null) total += Integer.parseInt(linea);
        System.out.println("Suma: " + total);
    }
}

// Salida esperada:
// Suma: 6
