// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainRecorridos.java Recorridos.java
// Ejecutar: java codetrace.practica.recorridos.MainRecorridos

package codetrace.practica.recorridos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * BFS y DFS sobre listas de adyacencia de un grafo dirigido.
 *
 * <p>BFS utiliza cola FIFO y descubre nodos por distancia en aristas
 * desde el origen, útil para caminos mínimos en grafos sin pesos.
 * DFS usa pila LIFO y profundiza antes de retroceder. Ambas recorren
 * O(V+E) tiempo si la representación permite acceder a vecinos.
 */
public final class Recorridos {
    private Recorridos() {}

    public static List<String> bfs(Map<String, List<String>> grafo, String inicio) {
        if (!grafo.containsKey(inicio)) throw new IllegalArgumentException("Origen ausente");
        List<String> orden = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        ArrayDeque<String> cola = new ArrayDeque<>();
        cola.add(inicio);
        visitados.add(inicio);
        while (!cola.isEmpty()) {
            String actual = cola.removeFirst();
            orden.add(actual);
            for (String vecino : grafo.getOrDefault(actual, List.of())) {
                if (visitados.add(vecino)) cola.addLast(vecino);
            }
        }
        return orden;
    }

    public static List<String> dfs(Map<String, List<String>> grafo, String inicio) {
        if (!grafo.containsKey(inicio)) throw new IllegalArgumentException("Origen ausente");
        List<String> orden = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        ArrayDeque<String> pila = new ArrayDeque<>();
        pila.push(inicio);
        while (!pila.isEmpty()) {
            String actual = pila.pop();
            if (!visitados.add(actual)) continue;
            orden.add(actual);
            List<String> vecinos = grafo.getOrDefault(actual, List.of());
            for (int i = vecinos.size() - 1; i >= 0; i--) {
                if (!visitados.contains(vecinos.get(i))) pila.push(vecinos.get(i));
            }
        }
        return orden;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainRecorridos.java Recorridos.java
 *   java codetrace.practica.recorridos.MainRecorridos
 * Nota: Recorridos.java es un componente; la salida corresponde al cliente MainRecorridos.java.
 * Salida esperada:
 *   BFS: [A, B, C, D, E]
 *   DFS: [A, B, D, C, E]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
