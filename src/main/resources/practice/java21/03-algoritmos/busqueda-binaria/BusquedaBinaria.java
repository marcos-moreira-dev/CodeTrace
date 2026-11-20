// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
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
