// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: usar desde el cliente de esta misma carpeta

package codetrace.practica.grafosgenericos;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Grafo dirigido y ponderado sobre vértices genéricos de identidad estable.
 *
 * <p>Inspirado en el toolkit Graph/Vertex/Edge del repositorio de
 * estructuras de datos: se almacena cada vértice y un mapa de
 * destinos hacia pesos de sus aristas.
 *
 * <p>Memoria O(V+E); consultar vecinos promedio O(1) por mapa.
 * Aquí se prohíben pesos no finitos, pero se permiten negativos
 * como propiedad de los datos. La elección del algoritmo es
 * responsabilidad de quien consulta el grafo.
 */
public final class GrafoGenerico<T> {
    private final Map<Vertice<T>, Map<Vertice<T>, Double>> adj =
            new LinkedHashMap<>();

    public void agregarVertice(Vertice<T> vertice) {
        adj.putIfAbsent(Objects.requireNonNull(vertice), new LinkedHashMap<>());
    }

    public void conectar(Vertice<T> desde, Vertice<T> hasta, double peso) {
        if (desde.equals(hasta) || !Double.isFinite(peso))
            throw new IllegalArgumentException("Grafo simple y pesos finitos");
        agregarVertice(desde);
        agregarVertice(hasta);
        adj.get(desde).put(hasta, peso);
    }

    public Map<Vertice<T>, Double> vecinos(Vertice<T> vertice) {
        Map<Vertice<T>, Double> vecinos = adj.get(vertice);
        if (vecinos == null) throw new IllegalArgumentException("Vértice ausente");
        return Collections.unmodifiableMap(new LinkedHashMap<>(vecinos));
    }

    public Set<Vertice<T>> vertices() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(adj.keySet()));
    }

    public int aristas() {
        return adj.values().stream().mapToInt(Map::size).sum();
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.grafosgenericos.MainGrafoGenerico
 * Nota: GrafoGenerico.java es un componente; la salida corresponde al cliente MainGrafoGenerico.java.
 * Salida esperada:
 *   Vértices: 3
 *   Latencias desde GYE: {UIO [Servidor[region=Quito, ip=10.0.0.2]]=12.0, CUE [Servidor[region=Cuenca, ip=10.0.0.3]]=18.0}
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
