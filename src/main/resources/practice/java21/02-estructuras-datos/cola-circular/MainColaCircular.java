// Compilar (desde esta carpeta): javac -encoding UTF-8 ColaCircular.java MainColaCircular.java
// Ejecutar: java MainColaCircular

/**
 * Cliente: cola FIFO circular implementada sobre un arreglo fijo.
 *
 * <p>Las posiciones avanzan con módulo capacidad para reutilizar
 * celdas liberadas; la implementación no desplaza los elementos.
 * Encolar/desencolar O(1), memoria O(capacidad).
 * Una cola vacía y una llena son estados distintos que el contrato
 * debe distinguir mediante un contador de elementos.
 */
public final class MainColaCircular {
    public static void main(String[] args) {
        ColaCircular cola = new ColaCircular(3);
        cola.encolar(10); cola.encolar(20); cola.encolar(30);
        if (cola.desencolar() != 10) throw new AssertionError();
        cola.encolar(40); // Prueba del índice que vuelve al comienzo.
        if (cola.desencolar() != 20 || cola.desencolar() != 30
                || cola.desencolar() != 40 || cola.cantidad() != 0)
            throw new AssertionError("No se conservó FIFO");
        System.out.println("FIFO y wrap-around verificados");
    }
}

/*
 * Salida esperada de consola:
 *   FIFO y wrap-around verificados
 * Se validan las extracciones 10, 20, 30, 40, pero solo se imprime esa línea.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 ColaCircular.java MainColaCircular.java
 *   java MainColaCircular
 * Salida esperada:
 *   FIFO y wrap-around verificados
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
