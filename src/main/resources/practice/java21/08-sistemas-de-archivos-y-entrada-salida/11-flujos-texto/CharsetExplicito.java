// Tema:
// UTF-8 puede necesitar varios bytes por carácter. ISO-8859-1 representa ciertos caracteres con uno.
// Leer bytes con una codificación distinta puede cambiar el texto.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 CharsetExplicito.java
// Ejecutar: java CharsetExplicito

import java.nio.charset.StandardCharsets;

public class CharsetExplicito {
    public static void main(String[] args) throws Exception {
        String palabra = "café";
        byte[] utf8 = palabra.getBytes(StandardCharsets.UTF_8);
        byte[] latin = palabra.getBytes(StandardCharsets.ISO_8859_1);
        System.out.println("UTF-8: " + utf8.length);
        System.out.println("Latin-1: " + latin.length);
        System.out.println(new String(utf8, StandardCharsets.UTF_8));
    }
}

// Salida esperada:
// UTF-8: 5
// Latin-1: 4
// café
