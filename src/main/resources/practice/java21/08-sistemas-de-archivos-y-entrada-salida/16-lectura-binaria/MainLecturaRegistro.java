// Tema:
// Formato binario fijo: identificador int de 4 bytes, cantidad int de 4 bytes, ambos big-endian.
// La lectura reproduce exactamente el orden de la escritura; no es serialización de objetos Java.
// Modalidad: R.
// Compilar: javac --release 21 -encoding UTF-8 RegistroBinario.java MainLecturaRegistro.java
// Ejecutar: java MainLecturaRegistro

import java.io.*;
import java.nio.file.*;

public class MainLecturaRegistro {
    public static void main(String[] args) throws IOException {
        Path archivo = Files.createTempFile(Path.of("."), "registro-", ".bin");
        try {
            try (DataOutputStream salida = new DataOutputStream(Files.newOutputStream(archivo))) {
                new RegistroBinario(17, 3).escribir(salida);
            }
            try (DataInputStream entrada = new DataInputStream(Files.newInputStream(archivo))) {
                RegistroBinario registro = RegistroBinario.leer(entrada);
                System.out.println(registro.id() + ": " + registro.cantidad());
            }
            System.out.println("Bytes: " + Files.size(archivo));
        } finally {
            Files.deleteIfExists(archivo);
        }
    }
}

// Salida esperada:
// 17: 3
// Bytes: 8
