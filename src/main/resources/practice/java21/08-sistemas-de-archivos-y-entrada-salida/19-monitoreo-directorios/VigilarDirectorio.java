// Tema:
// WatchService notifica cambios en un directorio registrado. Los eventos pueden agruparse o perderse.
// La espera tiene un límite y el ejemplo no supone que cada operación produzca exactamente un evento.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 VigilarDirectorio.java
// Ejecutar: java VigilarDirectorio

import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class VigilarDirectorio {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            try (WatchService vigilancia = dir.getFileSystem().newWatchService()) {
                dir.register(vigilancia, StandardWatchEventKinds.ENTRY_CREATE);
                Files.writeString(dir.resolve("nuevo.txt"), "dato");
                long fin = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
                boolean detectado = false;
                while (!detectado && System.nanoTime() < fin) {
                    WatchKey clave = vigilancia.poll(100, TimeUnit.MILLISECONDS);
                    if (clave == null) continue;
                    for (WatchEvent<?> evento : clave.pollEvents()) {
                        if (evento.kind() == StandardWatchEventKinds.ENTRY_CREATE
                                && Path.of("nuevo.txt").equals(evento.context())) detectado = true;
                    }
                    if (!clave.reset()) break;
                }
                System.out.println("Creacion observada: " + detectado);
            }
        } finally {
            // Se elimina únicamente el árbol temporal creado por este programa.
            try (var recorrido = Files.walk(dir)) {
                for (Path p : recorrido.sorted(java.util.Comparator.reverseOrder()).toList()) {
                    Files.deleteIfExists(p);
                }
            }
        }
    }
}

// Salida esperada:
// Creacion observada: true
// Alternativa en un sistema sin soporte: Creacion observada: false
