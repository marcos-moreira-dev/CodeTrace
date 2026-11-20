// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.grafo.MainGrafo

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
