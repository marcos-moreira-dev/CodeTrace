// Tema:
// AND conserva los bits comunes; OR combina; XOR detecta diferencias; NOT invierte. Se
// restringe la inversión a cuatro bits para visualizarla.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 OperacionesBits.java
// Ejecutar: java OperacionesBits

public class OperacionesBits {
    public static void main(String[] args) {
        int a = 0b1100, b = 0b1010;
        System.out.println(a & b);
        System.out.println(a | b);
        System.out.println(a ^ b);
        System.out.println((~a) & 0b1111);
    }
}

// Salida esperada:
// 8
// 14
// 6
// 3
