// Tema:
// BufferedWriter escribe caracteres que se codifican como bytes.
// El cierre vacía los datos pendientes; usamos UTF-8 explícitamente.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 EscrituraTexto.java
// Ejecutar: java EscrituraTexto

import java.nio.file.*;
import java.nio.charset.StandardCharsets;

public class EscrituraTexto {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory(Path.of("."), "demo-");
        try {
            Path archivo = dir.resolve("nota.txt");
            try (var escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
                escritor.write("café");
            }
            System.out.println(Files.readString(archivo, StandardCharsets.UTF_8));
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
// café
