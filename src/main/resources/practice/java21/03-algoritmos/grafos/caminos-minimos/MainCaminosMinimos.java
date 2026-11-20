// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en PowerShell (copia del paquete): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.grafoscaminos.MainCaminosMinimos

package codetrace.practica.grafoscaminos;

import java.util.List;

/**
 * Cliente para estudiar caminos mínimos y su elección algorítmica.
 *
 * <p>Inspirado en tu repositorio de grafos, pero con tres algoritmos
 * separados por sus precondiciones. La ruta entre servidores es un
 * grafo dirigido y ponderado: una arista representa la latencia de
 * enviar datos entre dos nodos, no una dependencia de herencia.
 *
 * <p>En un grafo con pesos no negativos, Dijkstra resuelve una fuente
 * mediante heap. Bellman-Ford tolera pesos negativos con mayor costo;
 * Floyd–Warshall sirve para consultar TODOS los pares cuando V es
 * manejable. BFS solo encuentra distancias mínimas por cantidad de
 * aristas si el grafo no está ponderado.
 *
 * <p>Se verifican distancias y reconstrucción de ruta. Un ciclo
 * negativo es distinto de un simple ciclo: hace que no exista
 * una distancia mínima finita para rutas que lo alcanzan y recorren.
 */
public final class MainCaminosMinimos {
    public static void main(String[] args) {
        GrafoPesado red = new GrafoPesado(5);
        red.conectar(0, 1, 4); // A -> B
        red.conectar(0, 2, 1); // A -> C
        red.conectar(2, 1, 2); // C -> B
        red.conectar(1, 3, 1); // B -> D
        red.conectar(2, 3, 5);
        red.conectar(3, 4, 3); // D -> E

        var dijkstra = CaminosMinimos.dijkstra(red, 0);
        var bellman = CaminosMinimos.bellmanFord(red, 0);
        double floyd = CaminosMinimos.floydWarshall(red)[0][4];
        System.out.println("Ruta A-E: " + dijkstra.camino(4));
        System.out.println("Dijkstra: " + dijkstra.distancia(4));
        System.out.println("Bellman-Ford: " + bellman.distancia(4));
        System.out.println("Floyd-Warshall: " + floyd);
        if (dijkstra.distancia(4) != 7.0 || bellman.distancia(4) != 7.0
                || floyd != 7.0 || !dijkstra.camino(4).equals(List.of(0, 2, 1, 3, 4)))
            throw new AssertionError("Ruta o distancia incorrecta");

        GrafoPesado descuentos = new GrafoPesado(3);
        descuentos.conectar(0, 1, 4);
        descuentos.conectar(1, 2, -2);
        descuentos.conectar(0, 2, 5);
        System.out.println("Bellman-Ford con arista negativa: "
                + CaminosMinimos.bellmanFord(descuentos, 0).distancia(2));
        try {
            CaminosMinimos.dijkstra(descuentos, 0);
            throw new AssertionError("Se aceptó un peso negativo en Dijkstra");
        } catch (IllegalArgumentException esperado) {
            System.out.println("Dijkstra rechaza pesos negativos correctamente");
        }
    }
}

/*
 * Salida esperada de consola:
 *   Ruta A-E: 
 *   Dijkstra: 
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
