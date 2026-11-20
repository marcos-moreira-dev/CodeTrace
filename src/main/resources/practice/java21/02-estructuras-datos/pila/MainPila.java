// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde la raíz java21 (PowerShell):
// javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-curso-java" MainPila

/**
 * Cliente de pila LIFO de capacidad fija implementada con arreglo.
 *
 * <p>El último elemento insertado sale primero. Apilar y desapilar
 * son O(1) y requieren O(capacidad) memoria. Una pila tiene usos
 * en backtracking, parsers y llamadas, aunque la pila de ejecución
 * de Java no sea este mismo objeto didáctico.
 */
public final class MainPila {
    public static void main(String[] args) {
        Pila<String> pila = new Pila<>();
        pila.apilar("A"); pila.apilar("B"); pila.apilar("C");
        if (!pila.desapilar().equals("C") || !pila.desapilar().equals("B")
                || pila.size() != 1) throw new AssertionError("No LIFO");
        System.out.println("LIFO comprobado");
    }
}
