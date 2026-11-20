// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia en PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: invocar desde el Main del mismo ejercicio

package codetrace.practica.grafosmst;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Prim y Kruskal para grafos NO dirigidos con pesos.
 *
 * <p>Ambos construyen un árbol de expansión mínima si el grafo es
 * conexo, o un bosque mínimo si hay varias componentes. Un MST NO
 * es un camino mínimo: minimiza la suma de aristas que conectan
 * todos los vértices sin ciclos, no el trayecto de un origen a otro.
 *
 * <p>Kruskal ordena aristas globalmente y usa Union-Find para evitar
 * ciclos. Prim expande componentes locales con una cola heap.
 * Kruskal O(E log E) y Prim O(E log E) con cola perezosa.
 */
public final class ExpansionMinima {
    public record Arista(int a, int b, int peso) { }
    public record Resultado(List<Arista> aristas, long costo, int componentes) { }

    private ExpansionMinima() {}

    private static void validar(int vertices, List<Arista> aristas) {
        if (vertices <= 0 || aristas == null)
            throw new IllegalArgumentException("Grafo inválido");
        for (Arista e : aristas) {
            if (e == null || e.a < 0 || e.b < 0 || e.a >= vertices || e.b >= vertices
                    || e.a == e.b) throw new IllegalArgumentException("Arista inválida");
        }
    }

    public static Resultado kruskal(int vertices, List<Arista> aristas) {
        validar(vertices, aristas);
        List<Arista> orden = new ArrayList<>(aristas);
        orden.sort(Comparator.comparingInt(Arista::peso));
        UnionFind uf = new UnionFind(vertices);
        List<Arista> seleccionadas = new ArrayList<>();
        long costo = 0;
        for (Arista e : orden) {
            if (uf.unir(e.a(), e.b())) {
                seleccionadas.add(e);
                costo += e.peso();
            }
        }
        return new Resultado(List.copyOf(seleccionadas), costo, uf.componentes());
    }

    public static Resultado prim(int vertices, List<Arista> aristas) {
        validar(vertices, aristas);
        List<List<Arista>> vecinos = new ArrayList<>();
        for (int v = 0; v < vertices; v++) vecinos.add(new ArrayList<>());
        for (Arista e : aristas) {
            vecinos.get(e.a()).add(e);
            vecinos.get(e.b()).add(new Arista(e.b(), e.a(), e.peso()));
        }
        boolean[] visto = new boolean[vertices];
        PriorityQueue<Arista> pq = new PriorityQueue<>(
                Comparator.comparingInt(Arista::peso));
        List<Arista> seleccionadas = new ArrayList<>();
        long costo = 0;
        int componentes = 0;
        for (int origen = 0; origen < vertices; origen++) {
            if (visto[origen]) continue;
            componentes++;
            visto[origen] = true;
            pq.addAll(vecinos.get(origen));
            while (!pq.isEmpty()) {
                Arista e = pq.poll();
                if (visto[e.b()]) continue;
                visto[e.b()] = true;
                seleccionadas.add(e);
                costo += e.peso();
                for (Arista siguiente : vecinos.get(e.b())) {
                    if (!visto[siguiente.b()]) pq.add(siguiente);
                }
            }
        }
        return new Resultado(List.copyOf(seleccionadas), costo, componentes);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.grafosmst.MainExpansionMinima
 * Nota: ExpansionMinima.java es un componente; la salida corresponde al cliente MainExpansionMinima.java.
 * Salida esperada:
 *   Prim:    [Arista[a=0, b=2, peso=1], Arista[a=2, b=1, peso=2], Arista[a=1, b=3, peso=4], Arista[a=3, b=4, peso=1]] = 8
 *   Kruskal: [Arista[a=0, b=2, peso=1], Arista[a=3, b=4, peso=1], Arista[a=1, b=2, peso=2], Arista[a=1, b=3, peso=4]] = 8
 *   Seis vértices, dos componentes: 2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
