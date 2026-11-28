// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . CaminosMinimos.java GrafoPesado.java MainCaminosMinimos.java MainCasosLimite.java
// Ejecutar: java codetrace.practica.grafoscaminos.MainCasosLimite

package codetrace.practica.grafoscaminos;

import java.util.List;

/**
 * Cliente de precondiciones y contraejemplos de caminos mínimos.
 *
 * <p>Un grafo puede contener un ciclo y aun así admitir caminos
 * mínimos; el problema surge cuando existe un ciclo de suma
 * NEGATIVA alcanzable y el recorrido puede repetirse indefinidamente.
 *
 * <p>Dijkstra se limita a pesos no negativos. Bellman-Ford rechaza
 * ciclos negativos alcanzables desde el origen; Floyd–Warshall
 * señala ciclos negativos en cualquier parte del grafo porque
 * estudia todos los pares. Las distancias a nodos inaccesibles
 * permanecen positivas infinitas y la ruta es una lista vacía.
 */
public final class MainCasosLimite {
    public static void main(String[] args) {
        GrafoPesado aislado = new GrafoPesado(3);
        aislado.conectar(0, 1, 2);
        var resultado = CaminosMinimos.dijkstra(aislado, 0);
        if (!Double.isInfinite(resultado.distancia(2))
                || !resultado.camino(2).equals(List.of()))
            throw new AssertionError("Inaccesibilidad incorrecta");
        System.out.println("Destino inaccesible: infinito y sin ruta");

        GrafoPesado negativo = new GrafoPesado(3);
        negativo.conectar(0, 1, 3);
        negativo.conectar(1, 2, -4);
        negativo.conectar(2, 1, 1); // ciclo de costo -3
        try {
            CaminosMinimos.bellmanFord(negativo, 0);
            throw new AssertionError("Bellman-Ford aceptó ciclo negativo");
        } catch (IllegalArgumentException esperado) {
            System.out.println("Bellman-Ford detectó ciclo negativo");
        }
        try {
            CaminosMinimos.floydWarshall(negativo);
            throw new AssertionError("Floyd-Warshall aceptó ciclo negativo");
        } catch (IllegalArgumentException esperado) {
            System.out.println("Floyd-Warshall detectó ciclo negativo");
        }
    }
}

/*
 * Salida esperada de consola:
 *   Destino inaccesible: infinito y sin ruta
 *   Bellman-Ford detectó ciclo negativo
 *   Floyd-Warshall detectó ciclo negativo
 * Verificación: el cliente termina sin excepciones inesperadas.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . CaminosMinimos.java GrafoPesado.java MainCaminosMinimos.java MainCasosLimite.java
 *   java codetrace.practica.grafoscaminos.MainCasosLimite
 * Salida esperada:
 *   Destino inaccesible: infinito y sin ruta
 *   Bellman-Ford detectó ciclo negativo
 *   Floyd-Warshall detectó ciclo negativo
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
