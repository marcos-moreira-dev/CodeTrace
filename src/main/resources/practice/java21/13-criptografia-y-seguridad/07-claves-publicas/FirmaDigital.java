// Tema:
// Una firma Ed25519 prueba integridad y posesión de la clave privada correspondiente, siempre que la clave pública sea confiable. El verificador no necesita la clave privada.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 FirmaDigital.java
// Ejecutar: java FirmaDigital

import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public class FirmaDigital {
    static boolean verificar(PublicKey publica, String mensaje, byte[] etiqueta) throws Exception {
        Signature firma = Signature.getInstance("Ed25519");
        firma.initVerify(publica);
        firma.update(mensaje.getBytes(StandardCharsets.UTF_8));
        return firma.verify(etiqueta);
    }

    public static void main(String[] args) throws Exception {
        KeyPair par = KeyPairGenerator.getInstance("Ed25519").generateKeyPair();
        Signature firma = Signature.getInstance("Ed25519");
        firma.initSign(par.getPrivate());
        firma.update("pedido=12".getBytes(StandardCharsets.UTF_8));
        byte[] etiqueta = firma.sign();
        System.out.println("Original: " + verificar(par.getPublic(), "pedido=12", etiqueta));
        System.out.println("Alterado: " + verificar(par.getPublic(), "pedido=99", etiqueta));
    }
}

// Salida esperada:
// Original: true
// Alterado: false
