// CodeTrace | Referencia: Java 21 LTS (JDK Temurin 21).
// Desde PowerShell, en una COPIA de la carpeta java21:
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\05-algoritmos\BusquedaBinaria.java'
// Sin main: es una clase o interfaz para compilar y reutilizar.

public final class BusquedaBinaria {
    public static int buscar(int[] ordenados, int objetivo) {
        int izquierda = 0;
        int derecha = ordenados.length - 1;
        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            if (ordenados[medio] == objetivo) return medio;
            if (ordenados[medio] < objetivo) izquierda = medio + 1;
            else derecha = medio - 1;
        }
        return -1;
    }
}
