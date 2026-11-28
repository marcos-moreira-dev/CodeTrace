// CodeTrace | Fundamentos - Especificacion oficial
// Referencia: Java SE 21, JLS §3.10.1
// Compilar: javac --release 21 -encoding UTF-8 LiteralesEnterosJls.java
// Ejecutar: java LiteralesEnterosJls
// Una misma cantidad admite distintas bases; los guiones bajos separan dígitos.

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
