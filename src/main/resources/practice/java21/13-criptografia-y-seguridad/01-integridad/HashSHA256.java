// Tema:
// SHA-256 produce una huella de 256 bits. Una huella no es cifrado ni prueba identidad; este ejemplo usa un vector conocido para comprobar el resultado.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 HashSHA256.java
// Ejecutar: java HashSHA256

import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public class HashSHA256 {
    public static void main(String[] args) throws Exception {
        byte[] mensaje = "abc".getBytes(StandardCharsets.UTF_8);
        byte[] huella = MessageDigest.getInstance("SHA-256").digest(mensaje);
        System.out.println("Bytes: " + huella.length);
        System.out.println(HexFormat.of().formatHex(huella));
    }
}

// Salida esperada:
// Bytes: 32
// ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad
