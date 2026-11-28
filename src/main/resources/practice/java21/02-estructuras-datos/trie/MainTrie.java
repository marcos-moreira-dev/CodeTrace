// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . MainTrie.java Trie.java
// Ejecutar: java codetrace.practica.estructuras.trie.MainTrie

package codetrace.practica.estructuras.trie;

/**
 * Cliente Trie: el prefijo no equivale a una palabra completa.
 *
 * <p>"casa" y "casco" comparten nodos iniciales, pero "cas" no se
 * insertó como palabra. Este caso permite observar búsqueda O(L)
 * donde L es la longitud, en contraste con un árbol BST que compara
 * cadenas completas. Map<Character,Nodo> intercambia memoria por
 * flexibilidad de alfabeto; un arreglo fijo sería otra decisión.
 */
public final class MainTrie {
    public static void main(String[] args) {
        Trie diccionario=new Trie();
        diccionario.insertar("casa");
        diccionario.insertar("casco");
        if(!diccionario.comienzaCon("cas") || diccionario.contiene("cas")
                || !diccionario.contiene("casa") || diccionario.contiene("cama"))
            throw new AssertionError("Prefijos");
        System.out.println("Prefijo 'cas': "+diccionario.comienzaCon("cas"));
    }
}

/*
 * Salida esperada de consola:
 *   Prefijo 'cas': true
 * El prefijo existe, aunque «cas» no fue insertada como palabra completa.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainTrie.java Trie.java
 *   java codetrace.practica.estructuras.trie.MainTrie
 * Salida esperada:
 *   Prefijo 'cas': true
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
