// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainUnionFind.java UnionFind.java
// Ejecutar: java codetrace.practica.estructuras.unionfind.MainUnionFind

package codetrace.practica.estructuras.unionfind;

/**
 * Cliente DSU/Union-Find: particionar elementos en componentes.
 *
 * <p>Se utiliza en Kruskal para decidir si agregar una arista crea
 * ciclo. No ofrece una ruta de vértices, solamente pertenencia al
 * mismo componente. Se comprueban uniones idempotentes, conexión
 * transitiva y contador de componentes; amortizado O(alfa(n)).
 */
public final class MainUnionFind {
    public static void main(String[] args) {
        UnionFind uf=new UnionFind(5);
        if(!uf.unir(0,1)||!uf.unir(1,2)||uf.unir(0,2))throw new AssertionError();
        if(!uf.conectados(0,2)||uf.conectados(0,4)||uf.componentes()!=3)
            throw new AssertionError("Componentes");
        System.out.println("Componentes: "+uf.componentes());
    }
}

/*
 * Salida esperada de consola:
 *   Componentes: 3
 * Las uniones (0,1) y (1,2) dejan {0,1,2}, {3} y {4}.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainUnionFind.java UnionFind.java
 *   java codetrace.practica.estructuras.unionfind.MainUnionFind
 * Salida esperada:
 *   Componentes: 3
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
