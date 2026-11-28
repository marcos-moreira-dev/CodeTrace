// Compilar (desde esta carpeta): javac -encoding UTF-8 Insercion.java
// Componente sin punto de entrada; no se ejecuta por separado.

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Insercion.java
 * Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
 * Salida esperada:
 *   (sin salida de consola; la comprobación termina correctamente)
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
