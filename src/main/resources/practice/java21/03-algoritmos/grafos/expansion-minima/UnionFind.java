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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.grafosmst.MainExpansionMinima
 * Nota: UnionFind.java es un componente; la salida corresponde al cliente MainExpansionMinima.java.
 * Salida esperada:
 *   Prim:    [Arista[a=0, b=2, peso=1], Arista[a=2, b=1, peso=2], Arista[a=1, b=3, peso=4], Arista[a=3, b=4, peso=1]] = 8
 *   Kruskal: [Arista[a=0, b=2, peso=1], Arista[a=3, b=4, peso=1], Arista[a=1, b=2, peso=2], Arista[a=1, b=3, peso=4]] = 8
 *   Seis vértices, dos componentes: 2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
