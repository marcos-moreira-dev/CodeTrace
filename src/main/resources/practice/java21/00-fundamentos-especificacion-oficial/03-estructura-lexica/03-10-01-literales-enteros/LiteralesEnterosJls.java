// Tema:
// Los prefijos 0x, 0 y 0b representan bases hexadecimal, octal y binaria. Sin prefijo se usa
// decimal. Los separadores entre dígitos no cambian el valor numérico.
// Compilar: javac --release 21 -encoding UTF-8 LiteralesEnterosJls.java
// Ejecutar: java LiteralesEnterosJls

public class LiteralesEnterosJls {
    public static void main(String[] args) {
        int decimal = 42;
        int hexadecimal = 0x2A;
        int octal = 052;
        int binario = 0b10_1010;
        System.out.println(decimal + " " + hexadecimal + " " + octal + " " + binario);
    }
}

// Salida esperada:
// 42 42 42 42
