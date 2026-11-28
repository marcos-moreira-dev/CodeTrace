// Tema:
// HMAC combina un secreto compartido con el mensaje para autenticarlo. Usamos una clave aleatoria de prueba; no se imprime la clave ni se compara una salida aleatoria con texto fijo.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 HmacSHA256.java
// Ejecutar: java HmacSHA256

import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSHA256 {
    static byte[] autenticar(byte[] clave, String mensaje) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(clave, "HmacSHA256"));
        return mac.doFinal(mensaje.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws Exception {
        byte[] clave = new byte[32];
        new SecureRandom().nextBytes(clave);
        byte[] etiqueta = autenticar(clave, "pedido 12");
        System.out.println("Bytes: " + etiqueta.length);
        System.out.println("Repetible con misma clave: " + MessageDigest.isEqual(etiqueta, autenticar(clave, "pedido 12")));
    }
}

// Salida esperada:
// Bytes: 32
// Repetible con misma clave: true
