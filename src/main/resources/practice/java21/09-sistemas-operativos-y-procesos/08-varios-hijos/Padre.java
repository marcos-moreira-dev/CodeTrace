// Tema:
// Se inician tres hijos independientes con argumentos distintos. El orden de impresión del padre no representa el orden real de ejecución.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Hijo.java Padre.java
// Ejecutar: java Padre

import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


public class Padre {
    // Usar el ejecutable de la JVM actual evita depender de PATH o de un shell.
    static ProcessBuilder comando(String clase, String... args) {
        String ejecutable = Path.of(System.getProperty("java.home"), "bin",
                System.getProperty("os.name").startsWith("Windows") ? "java.exe" : "java").toString();
        java.util.ArrayList<String> comando = new java.util.ArrayList<>();
        comando.add(ejecutable);
        comando.add("-Dfile.encoding=UTF-8");
        comando.add("-cp");
        comando.add(System.getProperty("java.class.path"));
        comando.add(clase);
        for (String arg : args) comando.add(arg);
        return new ProcessBuilder(comando);
    }

    static void esperar(Process proceso) throws Exception {
        if (!proceso.waitFor(8, TimeUnit.SECONDS)) {
            proceso.destroyForcibly();
            proceso.waitFor(2, TimeUnit.SECONDS);
            throw new IllegalStateException("Tiempo agotado");
        }
    }

    static String salida(Process proceso) throws Exception {
        esperar(proceso); // Salidas de estos hijos son pequeñas y acotadas.
        if (proceso.exitValue() != 0) throw new IllegalStateException("Fallo del hijo");
        return new String(proceso.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        Process[] hijos = new Process[3];
        try {
            for (int i = 0; i < hijos.length; i++)
                hijos[i] = comando("Hijo", Integer.toString(i + 1)).start();
            for (Process hijo : hijos) System.out.print(salida(hijo));
        } finally {
            for (Process hijo : hijos)
                if (hijo != null && hijo.isAlive()) hijo.destroyForcibly();
        }
    }
}

// Salida esperada:
// 1 -> 1
// 2 -> 4
// 3 -> 9
