// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia en PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: invocar desde el Main del mismo ejercicio

package codetrace.practica.grafosmst;

/**
 * Disjoint Set Union (DSU) / Union-Find de componentes disjuntas.
 *
 * <p>La compresión de caminos reduce la profundidad durante find.
 * Union by rank conecta la raíz más baja bajo la más alta.
 * El costo amortizado es casi constante, O(alfa(V)).
 *
 * <p>No es un grafo completo ni un recorrido: es la estructura
 * auxiliar que Kruskal necesita para detectar si una arista
 * conectaría vértices ya pertenecientes al mismo componente.
 */
public final class UnionFind {
    private final int[] padre, rango;
    private int componentes;

    public UnionFind(int vertices) {
        if (vertices <= 0) throw new IllegalArgumentException("Cantidad de vértices");
        padre = new int[vertices];
        rango = new int[vertices];
        componentes = vertices;
        for (int i = 0; i < vertices; i++) padre[i] = i;
    }

    public int encontrar(int id) {
        if (id < 0 || id >= padre.length) throw new IndexOutOfBoundsException(id);
        if (padre[id] != id) padre[id] = encontrar(padre[id]);
        return padre[id];
    }

    public boolean unir(int a, int b) {
        int raizA = encontrar(a), raizB = encontrar(b);
        if (raizA == raizB) return false;
        if (rango[raizA] < rango[raizB]) {
            padre[raizA] = raizB;
        } else if (rango[raizA] > rango[raizB]) {
            padre[raizB] = raizA;
        } else {
            padre[raizB] = raizA;
            rango[raizA]++;
        }
        componentes--;
        return true;
    }

    public int componentes() { return componentes; }
}
