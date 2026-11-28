// Tema:
// Hamming(7,4): posiciones 1,2,4 de paridad y 3,5,6,7 de datos. El síndrome identifica un bit erróneo bajo el supuesto de un solo error; no garantiza corrección de errores múltiples.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 Hamming74.java MainHamming.java
// Ejecutar: java MainHamming

public class Hamming74 {
    public static int[] codificar(int dato) {
        if (dato < 0 || dato > 15) throw new IllegalArgumentException("Cuatro bits");
        int[] b = new int[8]; // Posición cero no se usa.
        b[3] = (dato >>> 3) & 1;
        b[5] = (dato >>> 2) & 1;
        b[6] = (dato >>> 1) & 1;
        b[7] = dato & 1;
        b[1] = b[3] ^ b[5] ^ b[7];
        b[2] = b[3] ^ b[6] ^ b[7];
        b[4] = b[5] ^ b[6] ^ b[7];
        return b;
    }
    public static int sindrome(int[] b) {
        return (b[1] ^ b[3] ^ b[5] ^ b[7])
            + 2 * (b[2] ^ b[3] ^ b[6] ^ b[7])
            + 4 * (b[4] ^ b[5] ^ b[6] ^ b[7]);
    }
    public static int recuperar(int[] palabra) {
        int[] b = palabra.clone();
        int error = sindrome(b);
        if (error != 0) b[error] ^= 1;
        return (b[3] << 3) | (b[5] << 2) | (b[6] << 1) | b[7];
    }
}

// Salida esperada:
// Sindrome original: 0
// Posicion alterada: 5
// Dato recuperado: 11
