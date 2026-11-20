// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en PowerShell (copia del paquete): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: usar desde el cliente del mismo ejercicio

package codetrace.practica.grafosrepresentaciones;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista de adyacencia de un grafo simple sin pesos.
 *
 * <p>Memoria O(V+E), útil si hay pocas aristas respecto a V^2.
 * Enumerar los vecinos es O(grado). Comprobar una arista con
 * ArrayList.contains cuesta O(grado), a diferencia de una matriz.
 */
public final class ListaAdyacencia {
    private final List<List<Integer>> vecinos;
    private final boolean dirigido;

    public ListaAdyacencia(int vertices, boolean dirigido) {
        if (vertices <= 0) throw new IllegalArgumentException("Cantidad de vértices");
        this.dirigido = dirigido;
        vecinos = new ArrayList<>();
        for (int i = 0; i < vertices; i++) vecinos.add(new ArrayList<>());
    }

    public void conectar(int a, int b) {
        comprobar(a);
        comprobar(b);
        if (a == b) throw new IllegalArgumentException("Sin lazos");
        if (!vecinos.get(a).contains(b)) vecinos.get(a).add(b);
        if (!dirigido && !vecinos.get(b).contains(a)) vecinos.get(b).add(a);
    }

    public List<Integer> vecinos(int a) {
        comprobar(a);
        return List.copyOf(vecinos.get(a));
    }

    public boolean tieneArista(int a, int b) {
        comprobar(a);
        comprobar(b);
        return vecinos.get(a).contains(b);
    }

    private void comprobar(int a) {
        if (a < 0 || a >= vecinos.size()) throw new IndexOutOfBoundsException(a);
    }
}
