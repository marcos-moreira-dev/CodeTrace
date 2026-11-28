// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 ColaCircular.java MainColaCircular.java
// Ejecutar: java MainColaCircular

public final class ColaCircular {
    private final int[] datos;
    private int inicio;
    private int siguiente;
    private int cantidad;

    public ColaCircular(int capacidad) {
        if (capacidad <= 0) throw new IllegalArgumentException("Capacidad inválida");
        datos = new int[capacidad];
    }

    public void encolar(int valor) {
        if (cantidad == datos.length) throw new IllegalStateException("Cola llena");
        datos[siguiente] = valor;
        siguiente = (siguiente + 1) % datos.length;
        cantidad++;
    }

    public int desencolar() {
        if (cantidad == 0) throw new IllegalStateException("Cola vacía");
        int valor = datos[inicio];
        inicio = (inicio + 1) % datos.length;
        cantidad--;
        return valor;
    }

    public int cantidad() {
        return cantidad;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 ColaCircular.java MainColaCircular.java
 *   java MainColaCircular
 * Nota: ColaCircular.java es un componente; la salida corresponde al cliente MainColaCircular.java.
 * Salida esperada:
 *   FIFO y wrap-around verificados
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
