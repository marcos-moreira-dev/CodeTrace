// Tema:
// Cada JVM posee sus propias variables estáticas. Cambiar la variable del hijo no altera el estado del padre.
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
        // La clase Hijo también existe en esta JVM, con su propia variable estática.
        Hijo.cantidad = 10;
        Process hijo = comando("Hijo").start();
        try {
            System.out.print(salida(hijo));
            System.out.println("Padre: " + Hijo.cantidad);
        } finally {
            if (hijo.isAlive()) hijo.destroyForcibly();
        }
    }
}

// Salida esperada:
// Hijo: 15
// Padre: 10
