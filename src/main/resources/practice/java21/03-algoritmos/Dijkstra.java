// CodeTrace | Referencia: Java 21, grafos con pesos no negativos
// Compilar: javac --release 21 Dijkstra.java && java Dijkstra
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public final class Dijkstra {
    public record Arista(int destino, int costo) { }

    public static int[] menoresDistancias(List<List<Arista>> grafo, int inicio) {
        int[] distancia = new int[grafo.size()];
        Arrays.fill(distancia, Integer.MAX_VALUE);
        distancia[inicio] = 0;
        PriorityQueue<int[]> cola = new PriorityQueue<>(
                Comparator.comparingInt(par -> par[0]));
        cola.add(new int[]{0, inicio});
        while (!cola.isEmpty()) {
            int[] actual = cola.remove();
            int costo = actual[0], nodo = actual[1];
            if (costo != distancia[nodo]) continue; // Entrada obsoleta.
            for (Arista arista : grafo.get(nodo)) {
                if (arista.costo() < 0)
                    throw new IllegalArgumentException("Peso negativo");
                if (costo <= Integer.MAX_VALUE - arista.costo()
                        && costo + arista.costo() < distancia[arista.destino()]) {
                    int nuevo = costo + arista.costo();
                    distancia[arista.destino()] = nuevo;
                    cola.add(new int[]{nuevo, arista.destino()});
                }
            }
        }
        return distancia;
    }

    public static void main(String[] args) {
        var grafo = List.of(
                List.of(new Arista(1, 2), new Arista(2, 8)),
                List.of(new Arista(2, 3)),
                List.<Arista>of());
        System.out.println(Arrays.toString(menoresDistancias(grafo, 0)));
    }
}
