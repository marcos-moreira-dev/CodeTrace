// Compilar (desde esta carpeta): javac -encoding UTF-8 Callbacks.java Excepciones.java Lambdas.java Receptor.java ReferenciasYCallbacks.java
// Ejecutar: java Excepciones
public class Excepciones {
    public static int dividir(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisor cero");
        }
        return a / b;
    }

    public static void main(String[] args) {
        try {
            System.out.println(dividir(10, 0));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Callbacks.java Excepciones.java Lambdas.java Receptor.java ReferenciasYCallbacks.java
 *   java Excepciones
 * Salida esperada:
 *   Divisor cero
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
