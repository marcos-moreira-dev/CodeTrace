// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde la raíz java21 (PowerShell):
// javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-curso-java" MainListaSimple

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
