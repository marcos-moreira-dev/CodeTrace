// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . GrafoNoPonderado.java MainGrafo.java
// Ejecutar: java codetrace.practica.grafo.MainGrafo

package codetrace.practica.grafo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Grafo dirigido con lista de adyacencia de String.
 *
 * <p>Para cada vértice se almacenan sus vecinos. Esta representación
 * ocupa O(V+E) memoria, en lugar de una matriz VxV; es apropiada para
 * grafos dispersos. LinkedHashMap mantiene una iteración determinista
 * en estos ejemplos. La API expone listas inmutables a los clientes.
 */
public final class GrafoNoPonderado {
    private final Map<String, List<String>> adyacentes = new LinkedHashMap<>();

    public void agregarVertice(String id) {
        Objects.requireNonNull(id);
        adyacentes.putIfAbsent(id, new ArrayList<>());
    }

    public void conectar(String desde, String hacia) {
        agregarVertice(desde);
        agregarVertice(hacia);
        List<String> vecinos = adyacentes.get(desde);
        if (!vecinos.contains(hacia)) vecinos.add(hacia);
    }

    public List<String> vecinos(String id) {
        List<String> vecinos = adyacentes.get(id);
        if (vecinos == null) throw new IllegalArgumentException("Vértice inexistente: " + id);
        return List.copyOf(vecinos);
    }

    public boolean contiene(String id) { return adyacentes.containsKey(id); }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . GrafoNoPonderado.java MainGrafo.java
 *   java codetrace.practica.grafo.MainGrafo
 * Nota: GrafoNoPonderado.java es un componente; la salida corresponde al cliente MainGrafo.java.
 * Salida esperada:
 *   Vecinos de A: [B, C]
 *   Vecinos de B: [D]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
