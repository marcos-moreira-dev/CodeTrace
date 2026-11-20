// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.estructuras.listadoble.MainListaDoble

package codetrace.practica.estructuras.listadoble;

import java.util.List;

/**
 * Cliente Linked List doble: nodos con enlaces anterior y siguiente.
 *
 * <p>La lista simple permite navegar solo hacia delante. Una lista
 * doble facilita quitar el último nodo en O(1), a cambio de mantener
 * otra referencia por nodo. El dominio ListaDoble encapsula enlaces;
 * el cliente verifica recorrido en ambos sentidos, lista vacía y
 * la transición del último elemento a una estructura vacía.
 *
 * <p>Ejemplo autónomo de consola, sin MVC ni dependencias externas.
 */
public final class MainListaDoble {
    public static void main(String[] args) {
        var lista = new ListaDoble<String>();
        lista.agregarUltimo("A"); lista.agregarUltimo("B"); lista.agregarPrimero("Z");
        if (!lista.adelante().equals(List.of("Z", "A", "B"))
                || !lista.atras().equals(List.of("B", "A", "Z")))
            throw new AssertionError("Enlaces dobles");
        if (!lista.quitarUltimo().equals("B") || lista.cantidad() != 2)
            throw new AssertionError("Quitar último");
        System.out.println("Adelante: " + lista.adelante());
        System.out.println("Atrás: " + lista.atras());
    }
}

/*
 * Salida esperada de consola:
 *   Adelante: [Z, A]
 *   Atrás: [A, Z]
 * La impresión ocurre después de quitar el último elemento B.
 */
