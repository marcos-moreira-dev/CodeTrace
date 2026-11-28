// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . GrafoPonderado.java MainGrafoPonderado.java
// Ejecutar: java codetrace.practica.estructuras.grafoponderado.MainGrafoPonderado

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . GrafoPonderado.java MainGrafoPonderado.java
 *   java codetrace.practica.estructuras.grafoponderado.MainGrafoPonderado
 * Nota: GrafoPonderado.java es un componente; la salida corresponde al cliente MainGrafoPonderado.java.
 * Salida esperada:
 *   Aristas de origen: [Arista[destino=1, peso=4.5], Arista[destino=2, peso=7.0]]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
