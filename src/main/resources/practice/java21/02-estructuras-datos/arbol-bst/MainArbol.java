// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . ArbolBusqueda.java MainArbol.java
// Ejecutar: java codetrace.practica.bst.MainArbol

package codetrace.practica.bst;

/**
 * Cliente del árbol de búsqueda binaria: estructura, invariante y recorrido.
 *
 * <p>Insertar valores desordenados determina la forma del árbol. El
 * recorrido inorden devuelve los datos ascendentes gracias a la
 * propiedad izquierda-menor/derecha-mayor; no realiza un sort separado.
 * Buscar cuesta O(h), así que un BST sin balancear puede caer a O(n).
 *
 * <p>La clase cliente invoca operaciones sin manipular los nodos.
 */
public final class MainArbol {
    public static void main(String[] args) {
        ArbolBusqueda arbol = new ArbolBusqueda();
        for (int n : new int[]{8, 3, 10, 1, 6, 14, 4, 7}) arbol.insertar(n);
        System.out.println(arbol.recorridoInorden());
        System.out.println("Existe 6: " + arbol.contiene(6));
        System.out.println("Existe 2: " + arbol.contiene(2));
    }
}

/*
 * Salida esperada de consola:
 *   [1, 3, 4, 6, 7, 8, 10, 14]
 *   Existe 6: true
 *   Existe 2: false
 * El recorrido inorden refleja el invariante del BST.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ArbolBusqueda.java MainArbol.java
 *   java codetrace.practica.bst.MainArbol
 * Salida esperada:
 *   [1, 3, 4, 6, 7, 8, 10, 14]
 *   Existe 6: true
 *   Existe 2: false
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
