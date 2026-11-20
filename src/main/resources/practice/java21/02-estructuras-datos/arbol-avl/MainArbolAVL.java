// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.estructuras.avl.MainArbolAVL

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
