// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . ListaAdyacencia.java MainRepresentaciones.java MatrizAdyacencia.java
// Ejecutar: java codetrace.practica.grafosrepresentaciones.MainRepresentaciones

package codetrace.practica.grafosrepresentaciones;

/**
 * Cliente: matriz vs. lista de adyacencia sobre el MISMO grafo.
 *
 * <p>Con V vértices y E aristas, la matriz reserva V^2 casillas,
 * incluso si E es pequeño. La lista emplea O(V+E) memoria.
 * En matriz, consultar una arista es O(1); en esta lista ArrayList,
 * consultar cuesta O(grado) pero enumerar vecinos es natural.
 *
 * <p>Se prueba la misma propiedad con ambas implementaciones para
 * separar el modelo matemático de la representación en memoria.
 */
public final class MainRepresentaciones {
    public static void main(String[] args) {
        MatrizAdyacencia matriz = new MatrizAdyacencia(5, false);
        ListaAdyacencia lista = new ListaAdyacencia(5, false);
        int[][] conexiones = {{0, 1}, {0, 2}, {2, 3}, {3, 4}};
        for (int[] arista : conexiones) {
            matriz.conectar(arista[0], arista[1]);
            lista.conectar(arista[0], arista[1]);
        }
        System.out.println("Matriz 0-2: " + matriz.tieneArista(0, 2));
        System.out.println("Lista 0-2: " + lista.tieneArista(0, 2));
        System.out.println("Vecinos de 2: " + lista.vecinos(2));
        if (matriz.tieneArista(0, 2) != lista.tieneArista(0, 2))
            throw new AssertionError("Representaciones inconsistentes");
    }
}

/*
 * Salida esperada de consola:
 *   Matriz 0-2: true
 *   Lista 0-2: true
 *   Vecinos de 2: [0, 3]
 * Ambas representaciones modelan el mismo grafo no dirigido.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ListaAdyacencia.java MainRepresentaciones.java MatrizAdyacencia.java
 *   java codetrace.practica.grafosrepresentaciones.MainRepresentaciones
 * Salida esperada:
 *   Matriz 0-2: true
 *   Lista 0-2: true
 *   Vecinos de 2: [0, 3]
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
