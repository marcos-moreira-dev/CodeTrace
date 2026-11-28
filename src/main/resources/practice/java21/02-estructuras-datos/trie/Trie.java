// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainTrie.java Trie.java
// Ejecutar: java codetrace.practica.estructuras.trie.MainTrie

package codetrace.practica.estructuras.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Trie (árbol de prefijos) por caracteres UTF-16 de String Java.
 * Insertar y buscar palabra O(L) operaciones sobre un mapa, L
 * longitud de la cadena; espacio proporcional a caracteres/nodos.
 * Para Unicode fuera del BMP, un trie de code points sería distinto.
 */
public final class Trie {
    private static final class Nodo {
        Map<Character,Nodo> hijos = new HashMap<>();
        boolean fin;
    }
    private final Nodo raiz=new Nodo();

    public void insertar(String palabra) {
        if (palabra==null) throw new IllegalArgumentException("null");
        Nodo actual=raiz;
        for(char c:palabra.toCharArray())
            actual=actual.hijos.computeIfAbsent(c,k->new Nodo());
        actual.fin=true;
    }
    private Nodo nodo(String texto) {
        if(texto==null)throw new IllegalArgumentException("null");
        Nodo actual=raiz;
        for(char c:texto.toCharArray()) {
            actual=actual.hijos.get(c);
            if(actual==null)return null;
        }
        return actual;
    }
    public boolean contiene(String palabra) {
        Nodo n=nodo(palabra); return n!=null&&n.fin;
    }
    public boolean comienzaCon(String prefijo) { return nodo(prefijo)!=null; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainTrie.java Trie.java
 *   java codetrace.practica.estructuras.trie.MainTrie
 * Nota: Trie.java es un componente; la salida corresponde al cliente MainTrie.java.
 * Salida esperada:
 *   Prefijo 'cas': true
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
