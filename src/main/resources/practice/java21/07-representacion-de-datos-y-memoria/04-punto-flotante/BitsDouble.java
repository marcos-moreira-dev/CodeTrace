// Tema:
// IEEE 754 binary64 tiene un bit de signo, once de exponente y 52 de fracción. Un exponente
// de unos y fracción no nula representa NaN.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 BitsDouble.java
// Ejecutar: java BitsDouble

public class BitsDouble {
    public static void main(String[] args) {
        long bits = Double.doubleToRawLongBits(1.5);
        System.out.println(bits >>> 63);
        System.out.println((bits >>> 52) & 2047);
        System.out.println(bits & 0x000fffffffffffffL);
        System.out.println(Double.isNaN(Double.NaN));
    }
}

// Salida esperada:
// 0
// 1023
// 2251799813685248
// true
