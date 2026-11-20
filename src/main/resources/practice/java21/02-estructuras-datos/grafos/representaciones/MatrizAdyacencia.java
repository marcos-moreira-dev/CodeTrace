// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en PowerShell (copia del paquete): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: usar desde el cliente del mismo ejercicio

package codetrace.practica.grafosrepresentaciones;

/**
 * Matriz de adyacencia de un grafo simple sin pesos.
 *
 * <p>Acceso a existencia de arista O(1). Memoria O(V^2) independientemente
 * de cuántas aristas existan. Útil en grafos pequeños/densos y en
 * algoritmos que necesitan consultas rápidas de pares de vértices.
 */
public final class MatrizAdyacencia {
    private final boolean[][] aristas;
    private final boolean dirigido;

    public MatrizAdyacencia(int vertices, boolean dirigido) {
        if (vertices <= 0) throw new IllegalArgumentException("Cantidad de vértices");
        this.aristas = new boolean[vertices][vertices];
        this.dirigido = dirigido;
    }

    public void conectar(int a, int b) {
        comprobar(a);
        comprobar(b);
        if (a == b) throw new IllegalArgumentException("Sin lazos");
        aristas[a][b] = true;
        if (!dirigido) aristas[b][a] = true;
    }

    public boolean tieneArista(int a, int b) {
        comprobar(a);
        comprobar(b);
        return aristas[a][b];
    }

    public int vertices() { return aristas.length; }
    private void comprobar(int a) {
        if (a < 0 || a >= aristas.length) throw new IndexOutOfBoundsException(a);
    }
}
