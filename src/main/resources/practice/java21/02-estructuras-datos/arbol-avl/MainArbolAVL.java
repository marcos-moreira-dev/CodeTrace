// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . ArbolAVL.java MainArbolAVL.java
// Ejecutar: java codetrace.practica.estructuras.avl.MainArbolAVL

package codetrace.practica.estructuras.avl;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Cliente AVL: comparar el caso ordenado que degenera un BST simple.
 *
 * <p>Al insertar 1,2,...,15 en un BST sin balanceo, la altura sería
 * 15. Un AVL realiza rotaciones y garantiza altura logarítmica.
 * Se inspeccionan el orden inorden, altura y factores de balance.
 * Verificar invariante no es una operación habitual de producción,
 * sino una prueba didáctica que recorre O(n) nodos.
 */
public final class MainArbolAVL {
    public static void main(String[] args) {
        var arbol=new ArbolAVL();
        IntStream.rangeClosed(1,15).forEach(arbol::insertar);
        arbol.validar();
        if(arbol.altura()>5 || !arbol.inorden().equals(
            IntStream.rangeClosed(1,15).boxed().toList()))
            throw new AssertionError("AVL incorrecto");
        System.out.println("Altura del AVL: "+arbol.altura());
    }
}

/*
 * Salida esperada de consola:
 *   Altura del AVL: 4
 * Los valores 1..15 se equilibran en cuatro niveles; un BST simple
 * sin balanceo alcanzaría altura 15 con esa inserción ascendente.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ArbolAVL.java MainArbolAVL.java
 *   java codetrace.practica.estructuras.avl.MainArbolAVL
 * Salida esperada:
 *   Altura del AVL: 4
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
