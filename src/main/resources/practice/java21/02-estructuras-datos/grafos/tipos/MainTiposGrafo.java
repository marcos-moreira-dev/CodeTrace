// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en PowerShell (copia del paquete): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.grafostipos.MainTiposGrafo

package codetrace.practica.grafostipos;

import static codetrace.practica.grafostipos.Grafo.Orientacion.*;

/**
 * Cliente de taxonomía de grafos, no solo de sus recorridos.
 *
 * <p>Un grafo puede ser dirigido o no dirigido, ponderado o sin pesos,
 * conexo o desconectado, acíclico o con ciclos, bipartito, completo,
 * árbol o DAG. Son propiedades independientes: ponderado NO equivale
 * a dirigido y DAG NO equivale a árbol binario.
 *
 * <p>Representación: lista de adyacencia O(V+E). Para estudiar la
 * diferencia entre grafos dispersos y densos, ver el cliente de matriz
 * y lista. Para aplicar BFS/DFS, Dijkstra, Floyd–Warshall, Prim, Kruskal
 * u ordenamiento topológico, acudir a los clientes de algoritmos.
 *
 * <p>El ejercicio está autocontenido: Grafo.java define los invariantes
 * y este archivo construye instancias contrastantes y verifica su tipo.
 */
public final class MainTiposGrafo {
    public static void main(String[] args) {
        Grafo dag = new Grafo(DIRIGIDO, false);
        dag.conectar("Compilador", "Pruebas");
        dag.conectar("Pruebas", "Despliegue");
        System.out.println("Dirigido acíclico (DAG): " + dag.dag());
        dag.conectar("Despliegue", "Compilador");
        System.out.println("Después de introducir un ciclo: " + dag.dag());

        Grafo arbol = new Grafo(NO_DIRIGIDO, false);
        arbol.conectar("A", "B");
        arbol.conectar("A", "C");
        arbol.conectar("B", "D");
        System.out.println("Árbol=" + arbol.arbol()
                + ", bipartito=" + arbol.bipartito()
                + ", componentes=" + arbol.componentesConexas());
        arbol.vertice("AISLADO");
        System.out.println("Tras agregar un vértice aislado, componentes="
                + arbol.componentesConexas());

        Grafo ponderado = new Grafo(NO_DIRIGIDO, true);
        ponderado.conectar("GYE", "UIO", 4.5);
        ponderado.conectar("GYE", "CUE", 3.0);
        ponderado.conectar("UIO", "CUE", 2.0);
        System.out.println("Triángulo completo=" + ponderado.completo()
                + ", bipartito=" + ponderado.bipartito());
    }
}
