// Tema:
// ASCII define 128 caracteres; Unicode asigna puntos de código a un repertorio mayor. Un
// char de Java representa una unidad UTF-16, no siempre un carácter completo.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 AsciiUnicode.java
// Ejecutar: java AsciiUnicode

public class AsciiUnicode {
    public static void main(String[] args) {
        char a = 'A';
        char enye = 'ñ';
        System.out.println((int) a);
        System.out.println((int) enye);
        System.out.println(enye <= 127);
    }
}

// Salida esperada:
// 65
// 241
// false
