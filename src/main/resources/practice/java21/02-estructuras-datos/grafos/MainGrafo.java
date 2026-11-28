// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . GrafoNoPonderado.java MainGrafo.java
// Ejecutar: java codetrace.practica.grafo.MainGrafo

package codetrace.practica.grafo;

/**
 * Cliente de representación de grafos y dirección de aristas.
 *
 * <p>Una arista A->B no implica B->A. Esto permite modelar rutas de
 * sentido único o dependencias de software. El grafo almacena vértices
 * y vecinos; los recorridos BFS y DFS se enseñan por separado en
 * algoritmos, porque son operaciones distintas de la representación.
 */
public final class MainGrafo {
    public static void main(String[] args) {
        GrafoNoPonderado grafo = new GrafoNoPonderado();
        grafo.conectar("A", "B");
        grafo.conectar("A", "C");
        grafo.conectar("B", "D");
        System.out.println("Vecinos de A: " + grafo.vecinos("A"));
        System.out.println("Vecinos de B: " + grafo.vecinos("B"));
    }
}

/*
 * Salida esperada de consola:
 *   Vecinos de A: [B, C]
 *   Vecinos de B: [D]
 * Las aristas son dirigidas: conectar A→B no crea B→A.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . GrafoNoPonderado.java MainGrafo.java
 *   java codetrace.practica.grafo.MainGrafo
 * Salida esperada:
 *   Vecinos de A: [B, C]
 *   Vecinos de B: [D]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
