// Tema:
// Un certificado X.509 asocia una clave pública con un sujeto. Se genera un certificado autofirmado desechable mediante keytool del JDK y se analiza con CertificateFactory. Leer o verificar la autofirma no establece confianza en una identidad; no se instala ningún certificado.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 LeerCertificado.java MainCertificado.java
// Ejecutar: java MainCertificado

import java.io.IOException;
import java.nio.file.*;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public class MainCertificado {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("certificado-");
        Path almacen = dir.resolve("prueba.p12");
        try {
            String ejecutable = Path.of(System.getProperty("java.home"), "bin",
                    System.getProperty("os.name").startsWith("Windows") ? "keytool.exe" : "keytool").toString();
            // La contraseña es pública, ficticia y se usa solo para el almacén temporal.
            Process proceso = new ProcessBuilder(ejecutable, "-genkeypair", "-alias", "demo",
                    "-keyalg", "EC", "-groupname", "secp256r1", "-dname", "CN=CodeTrace Demo",
                    "-validity", "1", "-storetype", "PKCS12", "-keystore", almacen.toString(),
                    "-storepass", "prueba123", "-keypass", "prueba123", "-noprompt")
                    .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                    .redirectError(ProcessBuilder.Redirect.DISCARD).start();
            try {
                if (!proceso.waitFor(8, TimeUnit.SECONDS) || proceso.exitValue() != 0)
                    throw new IOException("No se genero certificado");
            } finally {
                if (proceso.isAlive()) {
                    proceso.destroyForcibly();
                    proceso.waitFor(2, TimeUnit.SECONDS);
                }
            }
            KeyStore ks = KeyStore.getInstance("PKCS12");
            try (var entrada = Files.newInputStream(almacen)) { ks.load(entrada, "prueba123".toCharArray()); }
            X509Certificate cert = LeerCertificado.leer(ks.getCertificate("demo").getEncoded());
            System.out.println("Tipo: " + cert.getType());
            System.out.println("Sujeto: " + cert.getSubjectX500Principal().getName());
            cert.verify(cert.getPublicKey());
            System.out.println("Autofirma valida");
        } finally {
            Files.deleteIfExists(almacen);
            Files.deleteIfExists(dir);
        }
    }
}

// Salida esperada:
// Tipo: X.509
// Sujeto: CN=CodeTrace Demo
// Autofirma valida
