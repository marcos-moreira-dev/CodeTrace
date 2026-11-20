// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en PowerShell (copia del paquete): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: invocar desde el cliente MainCaminosMinimos

package codetrace.practica.grafoscaminos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Tres problemas emparentados, pero no intercambiables.
 *
 * <p>Dijkstra: una fuente, pesos >= 0, cola de prioridad con
 * entradas obsoletas que se descartan; evitamos PQ.remove(v),
 * pues localizar un elemento arbitrario cuesta O(n).
 * Bellman-Ford: una fuente, admite aristas negativas y detecta
 * ciclos negativos alcanzables; O(VE).
 * Floyd–Warshall: todos los pares, O(V^3) tiempo, O(V^2)
 * memoria; detecta ciclos negativos que vuelven indefinidos
 * los caminos mínimos para ciertos pares.
 */
public final class CaminosMinimos {
    private CaminosMinimos() { }

    private record Pendiente(int vertice, double distancia) { }

    public static final class Resultado {
        private final double[] distancias;
        private final int[] anteriores;
        private final int origen;

        private Resultado(double[] distancias, int[] anteriores, int origen) {
            this.distancias = distancias;
            this.anteriores = anteriores;
            this.origen = origen;
        }

        public double distancia(int destino) {
            return distancias[destino];
        }

        public List<Integer> camino(int destino) {
            if (!Double.isFinite(distancias[destino])) return List.of();
            List<Integer> invertido = new ArrayList<>();
            for (int v = destino; v != -1; v = anteriores[v]) {
                invertido.add(v);
                if (v == origen) break;
            }
            Collections.reverse(invertido);
            return List.copyOf(invertido);
        }
    }

    private static Resultado inicializar(GrafoPesado grafo, int origen) {
        grafo.comprobar(origen);
        double[] dist = new double[grafo.vertices()];
        int[] prev = new int[grafo.vertices()];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(prev, -1);
        dist[origen] = 0.0;
        return new Resultado(dist, prev, origen);
    }

    /**
     * Dijkstra con inserción perezosa en la PriorityQueue.
     * O((V+E) log(V+E)) en la implementación sin decrease-key.
     */
    public static Resultado dijkstra(GrafoPesado grafo, int origen) {
        for (var arista : grafo.aristas())
            if (arista.peso() < 0) throw new IllegalArgumentException("Dijkstra: pesos negativos");
        Resultado r = inicializar(grafo, origen);
        PriorityQueue<Pendiente> pq = new PriorityQueue<>(
            Comparator.comparingDouble(Pendiente::distancia));
        pq.add(new Pendiente(origen, 0));
        while (!pq.isEmpty()) {
            Pendiente actual = pq.poll();
            if (Double.compare(actual.distancia(), r.distancias[actual.vertice()]) != 0)
                continue;
            for (var arista : grafo.vecinos(actual.vertice())) {
                double alternativa = actual.distancia() + arista.peso();
                int destino = arista.destino();
                if (alternativa < r.distancias[destino]) {
                    r.distancias[destino] = alternativa;
                    r.anteriores[destino] = actual.vertice();
                    pq.add(new Pendiente(destino, alternativa));
                }
            }
        }
        return r;
    }

    /** Admite pesos negativos, pero no ciclos negativos alcanzables. */
    public static Resultado bellmanFord(GrafoPesado grafo, int origen) {
        Resultado r = inicializar(grafo, origen);
        for (int pasada = 1; pasada < grafo.vertices(); pasada++) {
            boolean cambio = false;
            for (var arista : grafo.aristas()) {
                int desde = arista.origen(), hasta = arista.destino();
                if (Double.isInfinite(r.distancias[desde])) continue;
                double alternativa = r.distancias[desde] + arista.peso();
                if (alternativa < r.distancias[hasta]) {
                    r.distancias[hasta] = alternativa;
                    r.anteriores[hasta] = desde;
                    cambio = true;
                }
            }
            if (!cambio) break;
        }
        for (var arista : grafo.aristas()) {
            if (!Double.isInfinite(r.distancias[arista.origen()])
                    && r.distancias[arista.origen()] + arista.peso()
                        < r.distancias[arista.destino()]) {
                throw new IllegalArgumentException("Ciclo negativo alcanzable");
            }
        }
        return r;
    }

    /** Todos los pares: O(V^3) tiempo y O(V^2) memoria. */
    public static double[][] floydWarshall(GrafoPesado grafo) {
        int n = grafo.vertices();
        double[][] dist = new double[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Double.POSITIVE_INFINITY);
            dist[i][i] = 0.0;
        }
        for (var arista : grafo.aristas()) {
            int desde = arista.origen(), hasta = arista.destino();
            dist[desde][hasta] = Math.min(dist[desde][hasta], arista.peso());
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (Double.isInfinite(dist[i][k])) continue;
                for (int j = 0; j < n; j++) {
                    if (Double.isInfinite(dist[k][j])) continue;
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        for (int i = 0; i < n; i++)
            if (dist[i][i] < 0) throw new IllegalArgumentException("Ciclo negativo");
        return dist;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.grafoscaminos.MainCaminosMinimos
 * Nota: CaminosMinimos.java es un componente; la salida corresponde al cliente MainCaminosMinimos.java.
 * Salida esperada:
 *   Ruta A-E: [0, 2, 1, 3, 4]
 *   Dijkstra: 7.0
 *   Bellman-Ford: 7.0
 *   Floyd-Warshall: 7.0
 *   Bellman-Ford con arista negativa: 2.0
 *   Dijkstra rechaza pesos negativos correctamente
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
