// Tema:
// Una tubería conecta la salida de cada proceso con la entrada del siguiente. El generador emite números, el filtro conserva pares y el consumidor suma.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Generador.java Filtro.java Consumidor.java MainPipeline.java
// Ejecutar: java MainPipeline

import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


public class MainPipeline {
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
        java.util.List<Process> procesos = ProcessBuilder.startPipeline(
                java.util.List.of(comando("Generador"), comando("Filtro"), comando("Consumidor")));
        try {
            for (Process proceso : procesos) {
                esperar(proceso);
                if (proceso.exitValue() != 0) throw new IllegalStateException("Fallo en tuberia");
            }
            System.out.print(salida(procesos.get(2)));
        } finally {
            for (Process proceso : procesos)
                if (proceso.isAlive()) proceso.destroyForcibly();
        }
    }
}

// Salida esperada:
// Suma: 6
