// Tema:
// La redirección conecta los flujos estándar de un hijo con archivos. Se usan exclusivamente archivos temporales propios.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Hijo.java Padre.java
// Ejecutar: java Padre

import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.nio.file.Files;

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
        Path dir = Files.createTempDirectory(Path.of("."), "redireccion-");
        Path entrada = dir.resolve("entrada.txt");
        Path salida = dir.resolve("salida.txt");
        Path error = dir.resolve("error.txt");
        Process hijo = null;
        try {
            Files.writeString(entrada, "8\n");
            hijo = comando("Hijo").redirectInput(entrada.toFile())
                    .redirectOutput(salida.toFile()).redirectError(error.toFile()).start();
            esperar(hijo);
            if (hijo.exitValue() != 0) throw new IllegalStateException("Hijo fallo");
            System.out.println(Files.readString(salida).strip());
            System.out.println(Files.readString(error).strip());
        } finally {
            if (hijo != null && hijo.isAlive()) {
                hijo.destroyForcibly();
                hijo.waitFor(2, TimeUnit.SECONDS);
            }
            Files.deleteIfExists(entrada);
            Files.deleteIfExists(salida);
            Files.deleteIfExists(error);
            Files.deleteIfExists(dir);
        }
    }
}

// Salida esperada:
// 16
// Procesado
