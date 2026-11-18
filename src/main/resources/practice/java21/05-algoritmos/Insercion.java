// CodeTrace | Referencia: Java 21 LTS (JDK Temurin 21).
// Desde PowerShell, en una COPIA de la carpeta java21:
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\05-algoritmos\Insercion.java'
// Sin main: es una clase o interfaz para compilar y reutilizar.

public final class Insercion {
    public static void ordenar(int[] numeros) {
        for (int i = 1; i < numeros.length; i++) {
            int actual = numeros[i];
            int j = i - 1;
            while (j >= 0 && numeros[j] > actual) {
                numeros[j + 1] = numeros[j];
                j--;
            }
            numeros[j + 1] = actual;
        }
    }
}
