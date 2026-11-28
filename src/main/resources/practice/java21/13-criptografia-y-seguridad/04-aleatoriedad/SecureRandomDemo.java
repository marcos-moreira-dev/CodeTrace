// Tema:
// SecureRandom obtiene bytes adecuados para fines criptográficos mediante un proveedor del JDK. No se fija una semilla reproducible ni se imprime el secreto; la prueba comprueba el tamaño, no la aleatoriedad estadística.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 SecureRandomDemo.java
// Ejecutar: java SecureRandomDemo

import java.security.SecureRandom;

public class SecureRandomDemo {
    public static void main(String[] args) throws Exception {
        SecureRandom generador = new SecureRandom();
        byte[] nonce = new byte[12], sal = new byte[16];
        generador.nextBytes(nonce);
        generador.nextBytes(sal);
        System.out.println("Nonce: " + nonce.length + " bytes");
        System.out.println("Sal: " + sal.length + " bytes");
    }
}

// Salida esperada:
// Nonce: 12 bytes
// Sal: 16 bytes
