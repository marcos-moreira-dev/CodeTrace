// CodeTrace | Referencia: Java 21 LTS (JDK Temurin 21).
// Desde PowerShell, en una COPIA de la carpeta java21:
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\05-algoritmos\Euclides.java'
// Sin main: es una clase o interfaz para compilar y reutilizar.

public final class Euclides {
    private Euclides() {}

    public static int mcd(int a, int b) {
        if (a < 0 || b < 0) throw new IllegalArgumentException("Usa enteros no negativos");
        while (b != 0) {
            int resto = a % b;
            a = b;
            b = resto;
        }
        return a;
    }
}
