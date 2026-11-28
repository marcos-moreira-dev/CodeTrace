// Tema:
// UTF-8 codifica cada punto de código mediante uno o varios bytes. Los bytes de una ñ son
// c3 b1; no equivalen a dos letras.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 Utf8Bytes.java
// Ejecutar: java Utf8Bytes

import java.nio.charset.StandardCharsets;

public class Utf8Bytes {
    public static void main(String[] args) {
        byte[] bytes = "Añ".getBytes(StandardCharsets.UTF_8);
        for (byte b : bytes) {
            System.out.printf(java.util.Locale.ROOT, "%02x%n", Byte.toUnsignedInt(b));
        }
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }
}

// Salida esperada:
// 41
// c3
// b1
// Añ
