// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

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
