// Tema:
// Un entero tiene el mismo valor en todas las bases; solo cambia su representación textual.
// Integer convierte entre binario, decimal y hexadecimal.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 ConversionBases.java
// Ejecutar: java ConversionBases

public class ConversionBases {
    public static void main(String[] args) {
        int n = 42;
        System.out.println(Integer.toBinaryString(n));
        System.out.println(Integer.toHexString(n));
        System.out.println(Integer.parseInt("101010", 2));
    }
}

// Salida esperada:
// 101010
// 2a
// 42
