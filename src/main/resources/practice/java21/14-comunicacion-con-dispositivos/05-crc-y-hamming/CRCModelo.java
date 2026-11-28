// Tema:
// CRC: división polinómica en GF(2) mediante XOR, generador 1011. Se añade el resto al mensaje y se comprueba residuo cero. CRC32 del JDK usa otro polinomio y formato.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 CRCModelo.java
// Ejecutar: java CRCModelo

public class CRCModelo {
    static int resto(int bits) {
        for (int i = 30; i >= 3; i--)
            if ((bits & (1 << i)) != 0) bits ^= 0b1011 << (i - 3);
        return bits;
    }

    public static void main(String[] args) {
        int dato = 0b1101;
        int crc = resto(dato << 3);
        int palabra = (dato << 3) | crc;
        System.out.println("CRC: " + Integer.toBinaryString(crc));
        System.out.println("Valido: " + (resto(palabra) == 0));
        System.out.println("Alterado: " + (resto(palabra ^ 1) == 0));
        java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
        crc32.update("123456789".getBytes(java.nio.charset.StandardCharsets.US_ASCII));
        System.out.printf("CRC32 conocido: %08x%n", crc32.getValue());
    }
}

// Salida esperada:
// CRC: 1
// Valido: true
// Alterado: false
// CRC32 conocido: cbf43926
