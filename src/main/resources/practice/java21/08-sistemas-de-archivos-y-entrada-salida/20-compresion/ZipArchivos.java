// Tema:
// ZIP reúne varias entradas en un archivo. Para extraerlas se normaliza la ruta y se verifica que permanezca en el destino.
// Este ejemplo solo extrae su ZIP local, en un árbol nuevo sin enlaces; no es un extractor general de archivos no confiables.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 ZipArchivos.java
// Ejecutar: java ZipArchivos

import java.nio.file.*;
import java.io.*;
import java.util.zip.*;
import java.nio.charset.StandardCharsets;

public class ZipArchivos {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path zip = dir.resolve("ventas.zip");
            try (ZipOutputStream salida = new ZipOutputStream(Files.newOutputStream(zip))) {
                salida.putNextEntry(new ZipEntry("ventas/pan.txt"));
                salida.write("pan".getBytes(StandardCharsets.UTF_8));
                salida.closeEntry();
            }
            Path destino = Files.createDirectory(dir.resolve("extraido")).toAbsolutePath().normalize();
            try (ZipInputStream entrada = new ZipInputStream(Files.newInputStream(zip))) {
                ZipEntry item;
                while ((item = entrada.getNextEntry()) != null) {
                    Path ruta = destino.resolve(item.getName()).normalize();
                    if (!ruta.startsWith(destino) || ruta.equals(destino)) {
                        throw new IOException("Ruta fuera del destino");
                    }
                    if (item.isDirectory()) Files.createDirectories(ruta);
                    else {
                        Files.createDirectories(ruta.getParent());
                        Files.copy(entrada, ruta);
                    }
                    entrada.closeEntry();
                }
            }
            System.out.println(Files.readString(destino.resolve("ventas/pan.txt")));
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
// pan
