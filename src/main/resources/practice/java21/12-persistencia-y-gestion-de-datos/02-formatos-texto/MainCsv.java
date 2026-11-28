// Tema:
// CSV de subconjunto explícito: dos campos, nombre alfabético y cantidad entera no negativa. Se rechazan comillas, comas dentro de campos y saltos internos; no es un parser CSV general. El archivo usa UTF-8.
// Modalidad: ejecución real con archivos locales.
// Compilar: javac --release 21 -encoding UTF-8 CsvSimple.java MainCsv.java
// Ejecutar: java MainCsv

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MainCsv {
    public static void main(String[] args) throws Exception {
        Path ruta = Files.createTempFile("ventas-", ".csv");
        try {
            Files.write(ruta, List.of(CsvSimple.codificar("pan", 12), CsvSimple.codificar("leche", 7)),
                    StandardCharsets.UTF_8);
            for (String linea : Files.readAllLines(ruta, StandardCharsets.UTF_8)) {
                String[] campos = CsvSimple.analizar(linea);
                System.out.println(campos[0] + ": " + campos[1]);
            }
            try { CsvSimple.analizar("pan,leche,7"); }
            catch (IllegalArgumentException e) { System.out.println("Fila rechazada"); }
        } finally { Files.deleteIfExists(ruta); }
    }
}

// Salida esperada:
// pan: 12
// leche: 7
// Fila rechazada
