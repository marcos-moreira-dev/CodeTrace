// Tema:
// Un proceso dispone de entrada estándar, salida estándar y salida de errores.
// Los mensajes de error pueden redirigirse de forma independiente.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 EntradaSalidaError.java
// Ejecutar: java EntradaSalidaError (introducir 7 y pulsar Enter)

import java.io.*;

public class EntradaSalidaError {
    public static void main(String[] args) throws Exception {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in, java.nio.charset.StandardCharsets.UTF_8));
        String linea = entrada.readLine();
        System.out.println("Entrada: " + linea);
        System.err.println("Diagnostico");
    }
}

// Salida esperada:
// Entrada: 7
// Canal de error: Diagnostico
