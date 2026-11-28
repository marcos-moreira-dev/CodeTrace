// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . ListaAdyacencia.java MainRepresentaciones.java MatrizAdyacencia.java
// Ejecutar: java codetrace.practica.grafosrepresentaciones.MainRepresentaciones

package codetrace.practica.grafosrepresentaciones;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista de adyacencia de un grafo simple sin pesos.
 *
 * <p>Memoria O(V+E), útil si hay pocas aristas respecto a V^2.
 * Enumerar los vecinos es O(grado). Comprobar una arista con
 * ArrayList.contains cuesta O(grado), a diferencia de una matriz.
 */
public final class ListaAdyacencia {
    private final List<List<Integer>> vecinos;
    private final boolean dirigido;

    public ListaAdyacencia(int vertices, boolean dirigido) {
        if (vertices <= 0) throw new IllegalArgumentException("Cantidad de vértices");
        this.dirigido = dirigido;
        vecinos = new ArrayList<>();
        for (int i = 0; i < vertices; i++) vecinos.add(new ArrayList<>());
    }

    public void conectar(int a, int b) {
        comprobar(a);
        comprobar(b);
        if (a == b) throw new IllegalArgumentException("Sin lazos");
        if (!vecinos.get(a).contains(b)) vecinos.get(a).add(b);
        if (!dirigido && !vecinos.get(b).contains(a)) vecinos.get(b).add(a);
    }

    public List<Integer> vecinos(int a) {
        comprobar(a);
        return List.copyOf(vecinos.get(a));
    }

    public boolean tieneArista(int a, int b) {
        comprobar(a);
        comprobar(b);
        return vecinos.get(a).contains(b);
    }

    private void comprobar(int a) {
        if (a < 0 || a >= vecinos.size()) throw new IndexOutOfBoundsException(a);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ListaAdyacencia.java MainRepresentaciones.java MatrizAdyacencia.java
 *   java codetrace.practica.grafosrepresentaciones.MainRepresentaciones
 * Nota: ListaAdyacencia.java es un componente; la salida corresponde al cliente MainRepresentaciones.java.
 * Salida esperada:
 *   Matriz 0-2: true
 *   Lista 0-2: true
 *   Vecinos de 2: [0, 3]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
