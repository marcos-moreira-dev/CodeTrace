// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . ExpansionMinima.java MainExpansionMinima.java UnionFind.java
// Ejecutar: java codetrace.practica.grafosmst.MainExpansionMinima

package codetrace.practica.grafosmst;

import java.util.List;
import static codetrace.practica.grafosmst.ExpansionMinima.Arista;

/**
 * Cliente de Prim, Kruskal y Union-Find en un mismo grafo.
 *
 * <p>Supongamos que conectar cinco edificios tiene un costo por tramo.
 * Queremos que todos queden comunicados gastando lo menos posible,
 * no hallar el trayecto más corto desde una oficina. Este es el
 * problema del árbol de expansión mínima (MST).
 *
 * <p>Prim elige localmente la arista más barata que cruza la frontera
 * del componente visitado. Kruskal elige aristas globalmente y usa
 * conjuntos disjuntos para no formar ciclos. Ambos deben alcanzar
 * el MISMO costo total, aunque pueden elegir conjuntos diferentes
 * cuando hay empates.
 *
 * <p>Se comprueba también un grafo desconectado, que produce un
 * bosque de expansión mínima (una solución por componente).
 */
public final class MainExpansionMinima {
    public static void main(String[] args) {
        List<Arista> red = List.of(
            new Arista(0, 1, 3), new Arista(0, 2, 1),
            new Arista(1, 2, 2), new Arista(1, 3, 4),
            new Arista(2, 3, 5), new Arista(3, 4, 1),
            new Arista(2, 4, 8)
        );
        var prim = ExpansionMinima.prim(5, red);
        var kruskal = ExpansionMinima.kruskal(5, red);
        System.out.println("Prim:    " + prim.aristas() + " = " + prim.costo());
        System.out.println("Kruskal: " + kruskal.aristas() + " = " + kruskal.costo());
        if (prim.costo() != 8 || kruskal.costo() != 8
                || prim.aristas().size() != 4 || kruskal.componentes() != 1)
            throw new AssertionError("MST incorrecto");

        var bosque = ExpansionMinima.kruskal(6, red);
        System.out.println("Seis vértices, dos componentes: " + bosque.componentes());
        if (bosque.componentes() != 2) throw new AssertionError("Bosque incorrecto");
    }
}

/*
 * Salida esperada de consola:
 *   Prim:    [Arista[a=0, b=2, peso=1], Arista[a=2, b=1, peso=2], Arista[a=1, b=3, peso=4], Arista[a=3, b=4, peso=1]] = 8
 *   Kruskal: [Arista[a=0, b=2, peso=1], Arista[a=3, b=4, peso=1], Arista[a=1, b=2, peso=2], Arista[a=1, b=3, peso=4]] = 8
 *   Seis vértices, dos componentes: 2
 * Los algoritmos tienen igual costo pero pueden registrar aristas en otro orden.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ExpansionMinima.java MainExpansionMinima.java UnionFind.java
 *   java codetrace.practica.grafosmst.MainExpansionMinima
 * Salida esperada:
 *   Prim:    [Arista[a=0, b=2, peso=1], Arista[a=2, b=1, peso=2], Arista[a=1, b=3, peso=4], Arista[a=3, b=4, peso=1]] = 8
 *   Kruskal: [Arista[a=0, b=2, peso=1], Arista[a=3, b=4, peso=1], Arista[a=1, b=2, peso=2], Arista[a=1, b=3, peso=4]] = 8
 *   Seis vértices, dos componentes: 2
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
