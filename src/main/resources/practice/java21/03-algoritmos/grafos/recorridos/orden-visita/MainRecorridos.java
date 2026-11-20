// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.recorridos.MainRecorridos

package codetrace.practica.recorridos;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Cliente de BFS y DFS: un mismo grafo produce órdenes distintos.
 *
 * <p>En un mapa de dependencias, BFS visita primero las dependencias
 * a un salto del módulo principal; DFS recorre una cadena hasta el
 * fondo antes de pasar a otra. Una estructura grafo se distingue del
 * algoritmo de exploración que opera sobre ella.
 *
 * <p>Ambos son O(V+E) tiempo, O(V) memoria adicional; el resultado
 * depende del orden de vecinos, no es una ordenación universal.
 */
public final class MainRecorridos {
    public static void main(String[] args) {
        Map<String, List<String>> grafo = new LinkedHashMap<>();
        grafo.put("A", List.of("B", "C"));
        grafo.put("B", List.of("D"));
        grafo.put("C", List.of("E"));
        grafo.put("D", List.of());
        grafo.put("E", List.of());
        System.out.println("BFS: " + Recorridos.bfs(grafo, "A"));
        System.out.println("DFS: " + Recorridos.dfs(grafo, "A"));
    }
}

/*
 * Salida esperada de consola:
 *   BFS: [A, B, C, D, E]
 *   DFS: [A, B, D, C, E]
 * La diferencia procede de la cola FIFO y la pila LIFO, con vecinos ordenados.
 */
