// Tema:
// La paridad impar añade un bit para que el total de unos sea impar. No identifica la posición del error.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 ParidadImpar.java
// Ejecutar: java ParidadImpar

public class ParidadImpar {
    static boolean valido(int dato, int bit) {
        return ((Integer.bitCount(dato) + bit) & 1) == 1;
    }

    public static void main(String[] args) {
        int dato = 0b1010;
        int bit = 1 - (Integer.bitCount(dato) & 1);
        System.out.println("Paridad: " + bit);
        System.out.println("Original: " + valido(dato, bit));
        System.out.println("Alterado: " + valido(dato ^ 4, bit));
    }
}

// Salida esperada:
// Paridad: 1
// Original: true
// Alterado: false
