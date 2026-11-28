// Tema:
// PBKDF2 deriva una clave de una contraseña y una sal. La sal no es secreta; evita compartir la misma derivación entre registros. El costo de 20000 iteraciones es solo para esta práctica, no una recomendación de producción.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 PBKDF2Demo.java
// Ejecutar: java PBKDF2Demo

import java.security.*;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF2Demo {
    static byte[] derivar(char[] contrasena, byte[] sal) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(contrasena, sal, 20000, 256);
        try { return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(); }
        finally { spec.clearPassword(); }
    }

    public static void main(String[] args) throws Exception {
        byte[] sal = new byte[16];
        new SecureRandom().nextBytes(sal);
        char[] contrasena = {'d','e','m','o'};
        char[] distinta = {'o','t','r','a'};
        try {
            byte[] hash = derivar(contrasena, sal);
            System.out.println("Correcta: " + MessageDigest.isEqual(hash, derivar(contrasena, sal)));
            System.out.println("Incorrecta: " + MessageDigest.isEqual(hash, derivar(distinta, sal)));
        } finally {
            Arrays.fill(contrasena, '\0');
            Arrays.fill(distinta, '\0');
        }
    }
}

// Salida esperada:
// Correcta: true
// Incorrecta: false
