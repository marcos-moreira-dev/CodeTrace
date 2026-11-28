// Tema:
// char representa una unidad de código UTF-16. Las comillas simples delimitan el literal y los
// escapes Unicode se traducen antes de analizar los tokens.
// Compilar: javac --release 21 -encoding UTF-8 LiteralesCaracterJls.java
// Ejecutar: java LiteralesCaracterJls
// char representa una unidad UTF-16; se escribe entre comillas simples.

public class LiteralesCaracterJls {
    public static void main(String[] args) {
        char letra = 'A';
        char simbolo = '\u03A9';
        System.out.println(letra);
        System.out.println(simbolo);
    }
}

// Salida esperada:
// A
// Ω
