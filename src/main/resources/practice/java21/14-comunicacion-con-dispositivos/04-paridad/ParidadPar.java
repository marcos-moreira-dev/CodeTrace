// Tema:
// La paridad par hace que el número total de unos sea par. Detecta un número impar de cambios; dos cambios pueden pasar inadvertidos.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 ParidadPar.java
// Ejecutar: java ParidadPar

public class ParidadPar {
    static boolean valido(int dato, int bit) {
        return ((Integer.bitCount(dato) + bit) & 1) == 0;
    }

    public static void main(String[] args) {
        int dato = 0b1011;
        int bit = Integer.bitCount(dato) & 1;
        System.out.println("Paridad: " + bit);
        System.out.println("Original: " + valido(dato, bit));
        System.out.println("Un cambio: " + valido(dato ^ 1, bit));
        System.out.println("Dos cambios: " + valido(dato ^ 3, bit));
    }
}

// Salida esperada:
// Paridad: 1
// Original: true
// Un cambio: false
// Dos cambios: true
