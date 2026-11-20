// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: desde el cliente en esta misma carpeta

package codetrace.practica.recorridos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Árbol de descubrimiento de BFS, con distancias y predecesores.
 *
 * <p>Mientras DFS permite explorar profundidad, BFS encuentra un
 * camino de MÍNIMA cantidad de aristas en grafos sin pesos. Una
 * ruta mínima por cantidad de saltos no implica menor costo si
 * cada arista tuviera un peso distinto. El grafo está representado
 * con listas de adyacencia y se supone inmutable durante el recorrido.
 */
public final class CaminosSinPeso {
    private CaminosSinPeso() {}

    public record Resultado(Map<String, Integer> distancias,
                            Map<String, String> anteriores,
                            String origen) {
        public List<String> ruta(String destino) {
            if (!distancias.containsKey(destino)) return List.of();
            List<String> invertida = new ArrayList<>();
            for (String actual = destino; actual != null; actual = anteriores.get(actual)) {
                invertida.add(actual);
                if (actual.equals(origen)) break;
            }
            Collections.reverse(invertida);
            return List.copyOf(invertida);
        }
    }

    public static Resultado bfs(Map<String, List<String>> grafo, String origen) {
        if (grafo == null || !grafo.containsKey(origen))
            throw new IllegalArgumentException("Origen inexistente");
        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();
        ArrayDeque<String> cola = new ArrayDeque<>();
        cola.add(origen);
        distancias.put(origen, 0);
        while (!cola.isEmpty()) {
            String actual = cola.removeFirst();
            for (String vecino : grafo.getOrDefault(actual, List.of())) {
                if (!distancias.containsKey(vecino)) {
                    distancias.put(vecino, distancias.get(actual) + 1);
                    anteriores.put(vecino, actual);
                    cola.addLast(vecino);
                }
            }
        }
        return new Resultado(Map.copyOf(distancias), Map.copyOf(anteriores), origen);
    }

    /** Cuenta componentes de un grafo no dirigido, O(V+E). */
    public static int componentes(Map<String, List<String>> grafo) {
        Set<String> vistos = new HashSet<>();
        int total = 0;
        for (String v : grafo.keySet()) {
            if (!vistos.add(v)) continue;
            total++;
            ArrayDeque<String> cola = new ArrayDeque<>();
            cola.add(v);
            while (!cola.isEmpty()) {
                for (String vecino : grafo.getOrDefault(cola.removeFirst(), List.of()))
                    if (vistos.add(vecino)) cola.addLast(vecino);
            }
        }
        return total;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.recorridos.MainCaminosSinPeso
 * Nota: CaminosSinPeso.java es un componente; la salida corresponde al cliente MainCaminosSinPeso.java.
 * Salida esperada:
 *   Saltos a E: 2
 *   Ruta a E: [A, C, E]
 *   Componentes: 2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
