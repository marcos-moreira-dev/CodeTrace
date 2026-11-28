// Tema:
// AES-GCM proporciona confidencialidad e integridad autenticada. Se genera una clave nueva para esta demostración y un nonce de 12 bytes usado una sola vez al cifrar; en un sistema real se debe garantizar no reutilizar nonce con la misma clave. La etiqueta inválida se rechaza.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 AesGcmDemo.java
// Ejecutar: java AesGcmDemo

import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;

public class AesGcmDemo {
    public static void main(String[] args) throws Exception {
        KeyGenerator generador = KeyGenerator.getInstance("AES");
        generador.init(128);
        SecretKey clave = generador.generateKey();
        byte[] nonce = new byte[12];
        new SecureRandom().nextBytes(nonce);
        GCMParameterSpec parametros = new GCMParameterSpec(128, nonce);
        Cipher cifrador = Cipher.getInstance("AES/GCM/NoPadding");
        cifrador.init(Cipher.ENCRYPT_MODE, clave, parametros);
        byte[] cifrado = cifrador.doFinal("pan:12".getBytes(StandardCharsets.UTF_8));
        Cipher lector = Cipher.getInstance("AES/GCM/NoPadding");
        lector.init(Cipher.DECRYPT_MODE, clave, parametros);
        System.out.println(new String(lector.doFinal(cifrado), StandardCharsets.UTF_8));
        cifrado[0] ^= 1;
        try {
            Cipher alterado = Cipher.getInstance("AES/GCM/NoPadding");
            alterado.init(Cipher.DECRYPT_MODE, clave, parametros);
            alterado.doFinal(cifrado);
            throw new AssertionError("Se esperaba rechazo");
        } catch (AEADBadTagException e) {
            System.out.println("Etiqueta invalida");
        }
    }
}

// Salida esperada:
// pan:12
// Etiqueta invalida
