// Tema:
// La huella de un archivo se calcula por bloques, sin cargarlo entero. Comparamos contra una huella obtenida antes de modificar un archivo propio; en producción esa referencia debe ser confiable.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 VerificarArchivo.java
// Ejecutar: java VerificarArchivo

import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.nio.file.*;

public class VerificarArchivo {
    static byte[] huella(Path archivo) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bloque = new byte[256];
        try (var entrada = Files.newInputStream(archivo)) {
            int n;
            while ((n = entrada.read(bloque)) != -1) digest.update(bloque, 0, n);
        }
        return digest.digest();
    }

    public static void main(String[] args) throws Exception {
        Path archivo = Files.createTempFile("integridad-", ".txt");
        try {
            Files.writeString(archivo, "pan");
            byte[] referencia = huella(archivo);
            System.out.println("Integro: " + MessageDigest.isEqual(referencia, huella(archivo)));
            Files.writeString(archivo, "leche");
            System.out.println("Integro: " + MessageDigest.isEqual(referencia, huella(archivo)));
        } finally { Files.deleteIfExists(archivo); }
    }
}

// Salida esperada:
// Integro: true
// Integro: false
