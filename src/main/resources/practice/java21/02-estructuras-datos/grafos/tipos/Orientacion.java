// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . Grafo.java MainTiposGrafo.java Orientacion.java
// Ejecutar: java codetrace.practica.grafostipos.MainTiposGrafo
// Salida esperada: Dirigido acíclico (DAG): true; después de introducir un ciclo: false.
package codetrace.practica.grafostipos;

/** Orientación de las conexiones de un grafo, independiente de sus pesos. */
public enum Orientacion {
    DIRIGIDO, NO_DIRIGIDO
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . Grafo.java MainTiposGrafo.java Orientacion.java
 *   java codetrace.practica.grafostipos.MainTiposGrafo
 * Nota: Orientacion.java es un componente; la salida corresponde al cliente MainTiposGrafo.java.
 * Salida esperada:
 *   Dirigido acíclico (DAG): true
 *   Después de introducir un ciclo: false
 *   Árbol=true, bipartito=true, componentes=1
 *   Tras agregar un vértice aislado, componentes=2
 *   Triángulo completo=true, bipartito=false
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
