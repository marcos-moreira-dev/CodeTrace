// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . CaminosSinPeso.java MainCaminosSinPeso.java
// Ejecutar: java codetrace.practica.recorridos.MainCaminosSinPeso

package codetrace.practica.recorridos;

import java.util.List;
import java.util.Map;

/**
 * Cliente de BFS con reconstrucción de caminos y componentes.
 *
 * <p>Considera un grafo social NO PONDERADO: queremos llegar a
 * un usuario con el menor número de enlaces de amistad, no con
 * la menor latencia. BFS calcula distancia en saltos y guarda
 * predecesores para reconstruir la ruta.
 *
 * <p>En el segundo grafo, no dirigido, una persona aislada forma
 * otra componente. El conteo de componentes requiere un nuevo BFS
 * por cada origen no visitado. Tiempo O(V+E), memoria O(V).
 *
 * <p>Ver MainRecorridos para comparar el ORDEN de visitas BFS/DFS.
 * Este cliente estudia la información adicional que BFS permite.
 */
public final class MainCaminosSinPeso {
    public static void main(String[] args) {
        Map<String, List<String>> grafo = Map.of(
            "A", List.of("B", "C"), "B", List.of("D"),
            "C", List.of("E"), "D", List.of("E"), "E", List.of()
        );
        var resultado = CaminosSinPeso.bfs(grafo, "A");
        System.out.println("Saltos a E: " + resultado.distancias().get("E"));
        System.out.println("Ruta a E: " + resultado.ruta("E"));
        if (resultado.distancias().get("E") != 2
                || resultado.ruta("E").size() != 3)
            throw new AssertionError("BFS incorrecto");

        Map<String, List<String>> desconectado = Map.of(
            "A", List.of("B"), "B", List.of("A"), "C", List.of()
        );
        System.out.println("Componentes: " + CaminosSinPeso.componentes(desconectado));
    }
}

/*
 * Salida esperada de consola:
 *   Saltos a E: 2
 *   Ruta a E: [A, C, E]
 *   Componentes: 2
 * BFS encuentra una ruta mínima por número de aristas en este grafo sin pesos.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . CaminosSinPeso.java MainCaminosSinPeso.java
 *   java codetrace.practica.recorridos.MainCaminosSinPeso
 * Salida esperada:
 *   Saltos a E: 2
 *   Ruta a E: [A, C, E]
 *   Componentes: 2
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
