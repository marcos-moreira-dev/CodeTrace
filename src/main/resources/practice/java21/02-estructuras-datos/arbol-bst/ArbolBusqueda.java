// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . ArbolBusqueda.java MainArbol.java
// Ejecutar: java codetrace.practica.bst.MainArbol

package codetrace.practica.bst;

import java.util.ArrayList;
import java.util.List;

/**
 * Árbol binario de búsqueda (BST) sobre valores int distintos.
 *
 * <p>Todo valor en el subárbol izquierdo es menor que el nodo; todo
 * valor en el derecho es mayor. La propiedad permite buscar en O(h),
 * donde h es altura: O(log n) si está balanceado, O(n) si se degenera.
 * Este árbol no realiza rotaciones; no presentarlo como un AVL.
 */
public final class ArbolBusqueda {
    private static final class Nodo {
        final int dato;
        Nodo izquierda, derecha;
        Nodo(int dato) { this.dato = dato; }
    }

    private Nodo raiz;

    public void insertar(int dato) { raiz = insertar(raiz, dato); }

    private Nodo insertar(Nodo actual, int dato) {
        if (actual == null) return new Nodo(dato);
        if (dato < actual.dato) actual.izquierda = insertar(actual.izquierda, dato);
        if (dato > actual.dato) actual.derecha = insertar(actual.derecha, dato);
        return actual; // ignorar duplicados
    }

    public boolean contiene(int dato) {
        Nodo actual = raiz;
        while (actual != null) {
            if (dato == actual.dato) return true;
            actual = dato < actual.dato ? actual.izquierda : actual.derecha;
        }
        return false;
    }

    public List<Integer> recorridoInorden() {
        List<Integer> resultado = new ArrayList<>();
        inorden(raiz, resultado);
        return List.copyOf(resultado);
    }

    private void inorden(Nodo nodo, List<Integer> salida) {
        if (nodo == null) return;
        inorden(nodo.izquierda, salida);
        salida.add(nodo.dato);
        inorden(nodo.derecha, salida);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ArbolBusqueda.java MainArbol.java
 *   java codetrace.practica.bst.MainArbol
 * Nota: ArbolBusqueda.java es un componente; la salida corresponde al cliente MainArbol.java.
 * Salida esperada:
 *   [1, 3, 4, 6, 7, 8, 10, 14]
 *   Existe 6: true
 *   Existe 2: false
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
