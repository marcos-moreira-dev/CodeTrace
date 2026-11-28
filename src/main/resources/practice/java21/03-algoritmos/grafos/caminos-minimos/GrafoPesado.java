// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . CaminosMinimos.java GrafoPesado.java MainCaminosMinimos.java MainCasosLimite.java
// Ejecutar: java codetrace.practica.grafoscaminos.MainCaminosMinimos

package codetrace.practica.grafoscaminos;

import java.util.ArrayList;
import java.util.List;

/**
 * Grafo dirigido y ponderado, con aristas finitas y listas de adyacencia.
 *
 * <p>Se permiten pesos negativos porque Bellman-Ford y Floyd–Warshall
 * pueden manejarlos bajo ciertas condiciones. Dijkstra exige una
 * precondición más estricta (todos los pesos no negativos).
 */
public final class GrafoPesado {
    public record Arista(int origen, int destino, double peso) { }

    private final List<List<Arista>> adyacencia;
    private final List<Arista> aristas = new ArrayList<>();

    public GrafoPesado(int vertices) {
        if (vertices <= 0) throw new IllegalArgumentException("Número de vértices");
        adyacencia = new ArrayList<>();
        for (int i = 0; i < vertices; i++) adyacencia.add(new ArrayList<>());
    }

    public void conectar(int desde, int hasta, double peso) {
        comprobar(desde);
        comprobar(hasta);
        if (!Double.isFinite(peso)) throw new IllegalArgumentException("Peso no finito");
        Arista arista = new Arista(desde, hasta, peso);
        adyacencia.get(desde).add(arista);
        aristas.add(arista);
    }

    public int vertices() { return adyacencia.size(); }
    public List<Arista> vecinos(int id) {
        comprobar(id);
        return List.copyOf(adyacencia.get(id));
    }
    public List<Arista> aristas() { return List.copyOf(aristas); }

    public void comprobar(int id) {
        if (id < 0 || id >= vertices()) throw new IndexOutOfBoundsException(id);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . CaminosMinimos.java GrafoPesado.java MainCaminosMinimos.java MainCasosLimite.java
 *   java codetrace.practica.grafoscaminos.MainCaminosMinimos
 * Nota: GrafoPesado.java es un componente; la salida corresponde al cliente MainCaminosMinimos.java.
 * Salida esperada:
 *   Ruta A-E: [0, 2, 1, 3, 4]
 *   Dijkstra: 7.0
 *   Bellman-Ford: 7.0
 *   Floyd-Warshall: 7.0
 *   Bellman-Ford con arista negativa: 2.0
 *   Dijkstra rechaza pesos negativos correctamente
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
