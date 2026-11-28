// Tema:
// jcmd y jstack inspeccionan una JVM hija creada por el propio programa. El hijo anuncia que está listo y espera una señal por stdin; las herramientas tienen plazo y archivos de salida temporales. Si attach no está disponible se informa explícitamente, no se simula un resultado exitoso.
// Modalidad: dependiente de herramientas y permisos del sistema.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoInspeccionable.java MainDiagnostico.java
// Ejecutar: java MainDiagnostico

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MainDiagnostico {
    static Path herramienta(String nombre) {
        boolean windows = System.getProperty("os.name").startsWith("Windows");
        return Path.of(System.getProperty("java.home"), "bin", nombre + (windows ? ".exe" : ""));
    }
    static String inspeccionar(Path dir, long pid, String nombre, String argumento, String marca) throws Exception {
        Path ejecutable = herramienta(nombre);
        if (!Files.isRegularFile(ejecutable)) return "No disponible";
        Path salida = dir.resolve(nombre + ".txt");
        List<String> cmd = new ArrayList<>(List.of(ejecutable.toString(), Long.toString(pid)));
        if (argumento != null) cmd.add(argumento);
        Process p = new ProcessBuilder(cmd).redirectErrorStream(true).redirectOutput(salida.toFile()).start();
        try {
            if (!p.waitFor(4, TimeUnit.SECONDS)) return "No disponible";
            if (p.exitValue() != 0) return "No disponible";
            String texto = Files.readString(salida, StandardCharsets.UTF_8);
            if (!texto.contains(marca)) throw new AssertionError("Salida inesperada de " + nombre);
            return "OK";
        } finally {
            if (p.isAlive()) { p.destroyForcibly(); p.waitFor(1, TimeUnit.SECONDS); }
        }
    }

    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("diagnostico-");
        Path listo = dir.resolve("listo.txt");
        String cp = System.getProperty("java.class.path");
        Process hijo = new ProcessBuilder(herramienta("java").toString(), "-cp", cp, "ProcesoInspeccionable")
                .redirectOutput(listo.toFile()).redirectError(ProcessBuilder.Redirect.DISCARD).start();
        try {
            long limite = System.nanoTime() + TimeUnit.SECONDS.toNanos(4);
            while ((!Files.exists(listo) || !Files.readString(listo).contains("LISTO"))
                    && hijo.isAlive() && System.nanoTime() < limite) Thread.sleep(20);
            if (!hijo.isAlive() || !Files.readString(listo).contains("LISTO"))
                throw new IllegalStateException("Hijo no listo");
            System.out.println("jcmd: " + inspeccionar(dir, hijo.pid(), "jcmd", "VM.version", "JDK"));
            System.out.println("jstack: " + inspeccionar(dir, hijo.pid(), "jstack", null, "ProcesoInspeccionable.main"));
        } finally {
            try { hijo.getOutputStream().close(); }
            finally {
                if (!hijo.waitFor(2, TimeUnit.SECONDS)) {
                    hijo.destroyForcibly();
                    hijo.waitFor(2, TimeUnit.SECONDS);
                }
            }
            try (var archivos = Files.list(dir)) {
                for (Path ruta : archivos.toList()) Files.deleteIfExists(ruta);
            }
            Files.deleteIfExists(dir);
        }
    }
}

// Salida esperada:
// jcmd: OK
// jstack: OK
// Según disponibilidad: jcmd: OK; jstack: No disponible / jcmd: No disponible; jstack: OK / jcmd: No disponible; jstack: No disponible
