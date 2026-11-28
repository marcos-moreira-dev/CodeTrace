// Tema:
// X25519 permite que dos participantes calculen un secreto compartido utilizando su clave privada y la pública de la contraparte. El acuerdo no autentica identidades por sí solo; un protocolo real requiere autenticación y derivación de claves.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 AcuerdoX25519.java
// Ejecutar: java AcuerdoX25519

import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import javax.crypto.KeyAgreement;
import java.util.Arrays;

public class AcuerdoX25519 {
    static byte[] acordar(PrivateKey privada, PublicKey publica) throws Exception {
        KeyAgreement acuerdo = KeyAgreement.getInstance("X25519");
        acuerdo.init(privada);
        acuerdo.doPhase(publica, true);
        return acuerdo.generateSecret();
    }

    public static void main(String[] args) throws Exception {
        KeyPairGenerator generador = KeyPairGenerator.getInstance("X25519");
        KeyPair a = generador.generateKeyPair(), b = generador.generateKeyPair();
        byte[] secretoA = acordar(a.getPrivate(), b.getPublic());
        byte[] secretoB = acordar(b.getPrivate(), a.getPublic());
        System.out.println("Coinciden: " + MessageDigest.isEqual(secretoA, secretoB));
        System.out.println("Bytes: " + secretoA.length);
        Arrays.fill(secretoA, (byte) 0);
        Arrays.fill(secretoB, (byte) 0);
    }
}

// Salida esperada:
// Coinciden: true
// Bytes: 32
