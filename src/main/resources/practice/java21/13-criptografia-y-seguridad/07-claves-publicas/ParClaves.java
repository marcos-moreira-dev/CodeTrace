// Tema:
// Un par Ed25519 tiene clave pública para verificar y clave privada para firmar. Las claves se generan únicamente para la práctica; no se imprime ni guarda la privada.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 ParClaves.java
// Ejecutar: java ParClaves

import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public class ParClaves {
    public static void main(String[] args) throws Exception {
        KeyPair par = KeyPairGenerator.getInstance("Ed25519").generateKeyPair();
        System.out.println("Publica: " + par.getPublic().getFormat());
        System.out.println("Privada: " + par.getPrivate().getFormat());
    }
}

// Salida esperada:
// Publica: X.509
// Privada: PKCS#8
