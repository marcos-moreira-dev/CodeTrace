// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Euclides.java
// Componente sin punto de entrada; no se ejecuta por separado.

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Euclides.java
 * Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
 * Salida esperada:
 *   (sin salida de consola; la comprobación termina correctamente)
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
