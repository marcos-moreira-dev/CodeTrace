// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en PowerShell (copia del paquete): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: usar desde el cliente del mismo ejercicio

package codetrace.practica.grafostipos;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Grafo simple con una selección explícita de orientación y ponderación.
 *
 * <p>Un grafo simple no tiene lazos ni aristas paralelas. Este tipo
 * almacena una lista de adyacencia: O(V+E) memoria. En un grafo no
 * dirigido, cada arista aparece dos veces en los mapas de vecinos
 * porque se puede atravesar en ambas direcciones.
 *
 * <p>La estructura admite pesos finitos, incluso negativos, para estudiar
 * representaciones. Eso NO significa que Dijkstra admita pesos negativos.
 * El tipo no intenta decidir de antemano qué algoritmo utilizar.
 */
public final class Grafo {
    private final Orientacion orientacion;
    private final boolean ponderado;
    private final Map<String, LinkedHashMap<String, Double>> adyacencia =
            new LinkedHashMap<>();

    public Grafo(Orientacion orientacion, boolean ponderado) {
        this.orientacion = Objects.requireNonNull(orientacion);
        this.ponderado = ponderado;
    }

    public void vertice(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID vacío");
        adyacencia.computeIfAbsent(id, ignored -> new LinkedHashMap<>());
    }

    public void conectar(String desde, String hasta) {
        conectar(desde, hasta, 1.0);
    }

    public void conectar(String desde, String hasta, double peso) {
        if (desde == null || hasta == null || desde.equals(hasta))
            throw new IllegalArgumentException("Grafo simple: no hay lazos");
        if (!Double.isFinite(peso) || (!ponderado && peso != 1.0))
            throw new IllegalArgumentException("Peso no permitido");
        vertice(desde);
        vertice(hasta);
        adyacencia.get(desde).put(hasta, peso);
        if (!dirigido()) adyacencia.get(hasta).put(desde, peso);
    }

    public Set<String> vertices() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(adyacencia.keySet()));
    }

    public Map<String, Double> vecinos(String id) {
        if (!adyacencia.containsKey(id)) throw new IllegalArgumentException("Sin vértice: " + id);
        return Collections.unmodifiableMap(new LinkedHashMap<>(adyacencia.get(id)));
    }

    public boolean dirigido() { return orientacion == Orientacion.DIRIGIDO; }
    public boolean ponderado() { return ponderado; }
    public int cantidadVertices() { return adyacencia.size(); }

    public int cantidadAristas() {
        int sumatoria = adyacencia.values().stream().mapToInt(Map::size).sum();
        return dirigido() ? sumatoria : sumatoria / 2;
    }

    /** Componentes conexas: solo tiene sentido sin orientación. O(V+E). */
    public int componentesConexas() {
        if (dirigido()) throw new IllegalStateException("Usar conectividad fuerte o débil en dirigidos");
        Set<String> vistos = new HashSet<>();
        int componentes = 0;
        for (String origen : adyacencia.keySet()) {
            if (!vistos.add(origen)) continue;
            componentes++;
            ArrayDeque<String> cola = new ArrayDeque<>();
            cola.add(origen);
            while (!cola.isEmpty()) {
                for (String vecino : adyacencia.get(cola.removeFirst()).keySet())
                    if (vistos.add(vecino)) cola.addLast(vecino);
            }
        }
        return componentes;
    }

    public boolean conexo() {
        if (dirigido()) throw new IllegalStateException("No confundir conexo con fuertemente conexo");
        return cantidadVertices() > 0 && componentesConexas() == 1;
    }

    /**
     * K_2-coloración: un grafo no dirigido es bipartito si ningún borde
     * conecta vértices del mismo color. O(V+E).
     */
    public boolean bipartito() {
        if (dirigido()) throw new IllegalStateException("Se define aquí para no dirigidos");
        Map<String, Integer> color = new HashMap<>();
        for (String origen : adyacencia.keySet()) {
            if (color.containsKey(origen)) continue;
            color.put(origen, 0);
            ArrayDeque<String> cola = new ArrayDeque<>();
            cola.add(origen);
            while (!cola.isEmpty()) {
                String actual = cola.removeFirst();
                for (String vecino : adyacencia.get(actual).keySet()) {
                    Integer asignado = color.get(vecino);
                    if (asignado != null && asignado.equals(color.get(actual))) return false;
                    if (asignado == null) {
                        color.put(vecino, 1 - color.get(actual));
                        cola.addLast(vecino);
                    }
                }
            }
        }
        return true;
    }

    public boolean completo() {
        if (dirigido()) throw new IllegalStateException("Solo completo no dirigido aquí");
        for (String v : adyacencia.keySet()) {
            if (adyacencia.get(v).size() != cantidadVertices() - 1) return false;
        }
        return cantidadVertices() > 0;
    }

    /** DAG: dirigido, acíclico. DFS de tres estados, O(V+E). */
    public boolean dag() {
        if (!dirigido()) throw new IllegalStateException("DAG exige orientación");
        Map<String, Integer> estado = new HashMap<>();
        for (String v : adyacencia.keySet())
            if (estado.getOrDefault(v, 0) == 0 && cicloDesde(v, estado)) return false;
        return true;
    }

    private boolean cicloDesde(String v, Map<String, Integer> estado) {
        estado.put(v, 1); // Gris: en la pila actual
        for (String siguiente : adyacencia.get(v).keySet()) {
            int color = estado.getOrDefault(siguiente, 0);
            if (color == 1 || (color == 0 && cicloDesde(siguiente, estado))) return true;
        }
        estado.put(v, 2); // Negro: procesado
        return false;
    }

    public boolean arbol() {
        if (dirigido()) throw new IllegalStateException("Árbol no dirigido en este modelo");
        return conexo() && cantidadAristas() == cantidadVertices() - 1;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.grafostipos.MainTiposGrafo
 * Nota: Grafo.java es un componente; la salida corresponde al cliente MainTiposGrafo.java.
 * Salida esperada:
 *   Dirigido acíclico (DAG): true
 *   Después de introducir un ciclo: false
 *   Árbol=true, bipartito=true, componentes=1
 *   Tras agregar un vértice aislado, componentes=2
 *   Triángulo completo=true, bipartito=false
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
