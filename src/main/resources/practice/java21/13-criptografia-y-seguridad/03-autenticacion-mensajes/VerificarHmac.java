// Tema:
// Quien verifica debe poseer la clave compartida. Se recalcula la etiqueta y se compara con MessageDigest.isEqual, no con una comparación de texto. Cambiar el mensaje invalida la etiqueta.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 VerificarHmac.java
// Ejecutar: java VerificarHmac

import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class VerificarHmac {
    static byte[] autenticar(byte[] clave, String mensaje) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(clave, "HmacSHA256"));
        return mac.doFinal(mensaje.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws Exception {
        byte[] clave = new byte[32];
        new SecureRandom().nextBytes(clave);
        byte[] etiqueta = autenticar(clave, "cantidad=12");
        System.out.println("Original: " + MessageDigest.isEqual(etiqueta, autenticar(clave, "cantidad=12")));
        System.out.println("Modificado: " + MessageDigest.isEqual(etiqueta, autenticar(clave, "cantidad=99")));
    }
}

// Salida esperada:
// Original: true
// Modificado: false
