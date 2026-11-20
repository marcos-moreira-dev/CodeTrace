// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

package codetrace.practica.estructuras.grafoponderado;

import java.util.*;

/**
 * Grafo dirigido ponderado representado por listas de aristas.
 * addArista no agrega inversa. Un costo NEGATIVO es válido para
 * representar ciertos modelos, pero invalida Dijkstra. Aquí solo
 * se construye el dominio; no se fuerza un algoritmo de caminos.
 * Memoria O(V+E), inserción amortizada O(1).
 */
public final class GrafoPonderado {
    public record Arista(int destino,double peso) {}
    private final List<List<Arista>> ady;
    public GrafoPonderado(int n) {
        if(n<=0)throw new IllegalArgumentException("Vértices");
        ady=new ArrayList<>();
        for(int i=0;i<n;i++)ady.add(new ArrayList<>());
    }
    public void conectar(int desde,int hacia,double peso) {
        if(!Double.isFinite(peso))throw new IllegalArgumentException("Peso finito");
        ady.get(desde).add(new Arista(hacia,peso));
        ady.get(hacia); // Validación de índice destino.
    }
    public List<Arista> vecinos(int v) { return List.copyOf(ady.get(v)); }
}
