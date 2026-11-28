// Tema:
// GZIP comprime un flujo de bytes; no contiene un árbol de archivos.
// La descompresión debe reconstruir los bytes originales. No se presupone una tasa de compresión.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 GzipTexto.java
// Ejecutar: java GzipTexto

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.*;

public class GzipTexto {
    public static void main(String[] args) throws Exception {
        byte[] original = "pan pan pan pan".getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream comprimido = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(comprimido)) {
            gzip.write(original);
        }
        try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(comprimido.toByteArray()))) {
            System.out.println(new String(gzip.readAllBytes(), StandardCharsets.UTF_8));
        }
    }
}

// Salida esperada:
// pan pan pan pan
