// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 ListaSimple.java MainListaSimple.java
// Ejecutar: java MainListaSimple

/**
 * Cliente: lista simplemente enlazada genérica.
 *
 * <p>Cada nodo almacena su dato y un puntero al siguiente.
 * Insertar en cabeza cuesta O(1); recorrer buscando un elemento
 * requeriría O(n), y la implementación mínima no soporta índices.
 * No debe confundirse una lista enlazada con ArrayList.
 *
 * <p>Se verifica el tamaño en múltiples inserciones y el
 * comportamiento de un contenedor inicialmente vacío.
 */
public final class MainListaSimple {
    public static void main(String[] args) {
        ListaSimple<String> materias = new ListaSimple<>();
        if (materias.size() != 0) throw new AssertionError("Debe iniciar vacía");
        materias.insertarInicio("Matemática");
        materias.insertarInicio("Estructuras");
        materias.insertarInicio("Redes");
        if (materias.size() != 3) throw new AssertionError("Tamaño");
        System.out.println("Elementos insertados: " + materias.size());
    }
}

/*
 * Salida esperada de consola:
 *   Elementos insertados: 3
 * Insertar al inicio aumenta el tamaño de cero a tres elementos.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 ListaSimple.java MainListaSimple.java
 *   java MainListaSimple
 * Salida esperada:
 *   Elementos insertados: 3
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
