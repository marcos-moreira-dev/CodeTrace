// Tema:
// IEEE 754 binary32 contiene un bit de signo, ocho de exponente sesgado y 23 de fracción.
// Para -6.5 el exponente almacenado es 129.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 BitsFloat.java
// Ejecutar: java BitsFloat

public class BitsFloat {
    public static void main(String[] args) {
        int bits = Float.floatToRawIntBits(-6.5f);
        System.out.println("Signo: " + (bits >>> 31));
        System.out.println("Exponente: " + ((bits >>> 23) & 255));
        System.out.println("Fraccion: " + (bits & 0x7fffff));
        System.out.println(Float.isInfinite(Float.POSITIVE_INFINITY));
    }
}

// Salida esperada:
// Signo: 1
// Exponente: 129
// Fraccion: 5242880
// true
