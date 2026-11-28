// Tema:
// Microservicios mínimos: disponibilidad y reservas se ejecutan en JVM hijas distintas, comunicadas por HTTP en loopback y puertos efímeros. Cada servicio posee estado privado. Se omiten persistencia, autenticación, transacciones distribuidas y despliegue de producción; un fallo intermedio puede dejar una reserva incompleta.
// Rol: Orquestador didáctico: arranca dos JVM propias, recibe puertos asignados, prueba HTTP y detiene ambos procesos aun ante errores.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build disponibilidad/ServicioDisponibilidad.java reservas/ServicioReservas.java cliente/ClienteAlquiler.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

import cliente.ClienteAlquiler;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    private static Process iniciar(Path salida, String clase, String... args) throws Exception {
        String ejecutable = Path.of(System.getProperty("java.home"), "bin",
            System.getProperty("os.name").startsWith("Windows") ? "java.exe" : "java").toString();
        List<String> comando = new ArrayList<>(List.of(ejecutable, "-cp",
                System.getProperty("java.class.path"), clase));
        comando.addAll(Arrays.asList(args));
        return new ProcessBuilder(comando).redirectOutput(salida.toFile())
                .redirectError(ProcessBuilder.Redirect.DISCARD).start();
    }
    private static int puerto(Process p, Path salida) throws Exception {
        long limite = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
        while (p.isAlive() && System.nanoTime() < limite) {
            String dato = Files.exists(salida) ? Files.readString(salida) : "";
            if (dato.contains("\n")) {
                int puerto = Integer.parseInt(dato.lines().findFirst().orElseThrow().strip());
                if (puerto > 0 && puerto <= 65535) return puerto;
                throw new IllegalStateException("Puerto invalido");
            }
            Thread.sleep(20);
        }
        throw new IllegalStateException("Servicio no listo");
    }
    private static void detener(Process p) throws Exception {
        if (p == null) return;
        try { p.getOutputStream().close(); }
        finally {
            if (!p.waitFor(2, TimeUnit.SECONDS)) {
                p.destroyForcibly();
                if (!p.waitFor(2, TimeUnit.SECONDS)) throw new IllegalStateException("Hijo activo");
            }
        }
    }
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("bicicletas-");
        Path salidaA = dir.resolve("disponibilidad.txt"), salidaB = dir.resolve("reservas.txt");
        Process a = null, b = null;
        try {
            a = iniciar(salidaA, "disponibilidad.ServicioDisponibilidad");
            int pa = puerto(a, salidaA);
            b = iniciar(salidaB, "reservas.ServicioReservas", Integer.toString(pa));
            int pb = puerto(b, salidaB);
            ClienteAlquiler.probar(pa, pb);
        } finally {
            try { detener(b); }
            finally {
                try { detener(a); }
                finally {
                    Files.deleteIfExists(salidaB);
                    Files.deleteIfExists(salidaA);
                    Files.deleteIfExists(dir);
                }
            }
        }
    }
}

// Salida esperada:
// Disponibles: 1
// Reserva: R1
// Disponibles: 0
// Reserva rechazada
