// Tema:
// Un certificado X.509 asocia una clave pública con un sujeto. Se genera un certificado autofirmado desechable mediante keytool del JDK y se analiza con CertificateFactory. Leer o verificar la autofirma no establece confianza en una identidad; no se instala ningún certificado.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 LeerCertificado.java MainCertificado.java
// Ejecutar: java MainCertificado

import java.io.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class LeerCertificado {
    public static X509Certificate leer(byte[] der) throws Exception {
        try (ByteArrayInputStream entrada = new ByteArrayInputStream(der)) {
            return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(entrada);
        }
    }
}

// Salida esperada:
// Tipo: X.509
// Sujeto: CN=CodeTrace Demo
// Autofirma valida
