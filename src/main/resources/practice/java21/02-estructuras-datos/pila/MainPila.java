// Compilar (desde esta carpeta): javac -encoding UTF-8 MainPila.java Pila.java
// Ejecutar: java MainPila

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

/*
 * Salida esperada de consola:
 *   LIFO comprobado
 * Desapilar devuelve C, luego B; solo A permanece en la pila.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 MainPila.java Pila.java
 *   java MainPila
 * Salida esperada:
 *   LIFO comprobado
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
