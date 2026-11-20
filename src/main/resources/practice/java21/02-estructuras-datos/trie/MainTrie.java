// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.estructuras.trie.MainTrie

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
 *   Prefijo 'cas': 
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
