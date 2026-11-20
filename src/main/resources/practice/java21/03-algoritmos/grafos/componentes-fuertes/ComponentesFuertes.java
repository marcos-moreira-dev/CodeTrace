// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: usar desde el cliente de la misma carpeta

package codetrace.practica.grafosscc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Componentes fuertemente conexas de un grafo dirigido (Kosaraju).
 *
 * <p>Dos vértices están fuertemente conectados cuando hay un camino
 * dirigido en AMBOS sentidos. Una componente conexa de un grafo
 * no dirigido no es el mismo concepto.
 *
 * <p>Primera DFS: registrar tiempos de finalización. Se invierten
 * todas las aristas y una segunda DFS en orden decreciente de
 * finalización encuentra las componentes. O(V+E) tiempo y memoria.
 *
 * <p>La versión recursiva es didáctica: para grafos enormes es
 * preferible una pila explícita o controlar profundidad.
 */
public final class ComponentesFuertes {
    private ComponentesFuertes() {}

    private static <T> void finalizar(T v, Map<T, List<T>> grafo,
            Set<T> vistos, List<T> orden) {
        if (!vistos.add(v)) return;
        for (T w : grafo.get(v)) finalizar(w, grafo, vistos, orden);
        orden.add(v);
    }

    private static <T> void recolectar(T v, Map<T, List<T>> invertido,
            Set<T> vistos, Set<T> componente) {
        if (!vistos.add(v)) return;
        componente.add(v);
        for (T w : invertido.get(v)) recolectar(w, invertido, vistos, componente);
    }

    public static <T> List<Set<T>> kosaraju(Map<T, List<T>> entrada) {
        if (entrada == null) throw new IllegalArgumentException("Grafo nulo");
        Map<T, List<T>> grafo = new LinkedHashMap<>();
        for (var e : entrada.entrySet()) {
            if (e.getKey() == null || e.getValue() == null)
                throw new IllegalArgumentException("Vértices/vecinos nulos");
            grafo.put(e.getKey(), List.copyOf(e.getValue()));
        }
        for (List<T> vecinos : entrada.values()) {
            for (T w : vecinos) grafo.putIfAbsent(w, List.of());
        }

        Map<T, List<T>> invertido = new LinkedHashMap<>();
        for (T v : grafo.keySet()) invertido.put(v, new ArrayList<>());
        for (var e : grafo.entrySet())
            for (T w : e.getValue()) invertido.get(w).add(e.getKey());

        List<T> orden = new ArrayList<>();
        Set<T> vistos = new HashSet<>();
        for (T v : grafo.keySet()) finalizar(v, grafo, vistos, orden);
        Collections.reverse(orden);

        vistos.clear();
        List<Set<T>> resultado = new ArrayList<>();
        for (T v : orden) {
            if (vistos.contains(v)) continue;
            Set<T> componente = new LinkedHashSet<>();
            recolectar(v, invertido, vistos, componente);
            resultado.add(Collections.unmodifiableSet(componente));
        }
        return List.copyOf(resultado);
    }
}
