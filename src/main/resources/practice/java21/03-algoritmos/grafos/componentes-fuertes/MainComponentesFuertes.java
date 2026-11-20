// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.grafosscc.MainComponentesFuertes

package codetrace.practica.grafosscc;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Cliente de conectividad FUERTE en un grafo dirigido.
 *
 * <p>Supongamos un sistema de servicios donde una conexión A->B
 * permite llamar de A a B, pero NO necesariamente lo contrario.
 * Un grupo de servicios fuertemente conexo puede alcanzar a
 * cualquier otro del mismo grupo siguiendo flechas.
 *
 * <p>A,B,C forman un ciclo, D,E otro, y F está aislado. La
 * conexión C->D no fusiona componentes porque falta camino
 * de regreso D->C. Esta diferencia es importante frente a
 * tratar erróneamente el grafo como no dirigido.
 *
 * <p>Kosaraju usa dos recorridos DFS y el grafo invertido:
 * O(V+E), con memoria auxiliar O(V+E).
 */
public final class MainComponentesFuertes {
    public static void main(String[] args) {
        Map<String, List<String>> grafo = Map.of(
            "A", List.of("B"), "B", List.of("C"),
            "C", List.of("A", "D"),
            "D", List.of("E"), "E", List.of("D"),
            "F", List.of()
        );
        List<Set<String>> componentes = ComponentesFuertes.kosaraju(grafo);
        System.out.println("Componentes fuertes: " + componentes);
        if (componentes.size() != 3
                || !componentes.contains(Set.of("A", "B", "C"))
                || !componentes.contains(Set.of("D", "E"))
                || !componentes.contains(Set.of("F")))
            throw new AssertionError("SCC incorrectas");
    }
}
