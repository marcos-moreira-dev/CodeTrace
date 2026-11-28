// Compilar (desde esta carpeta): javac -encoding UTF-8 Callbacks.java Excepciones.java Lambdas.java Receptor.java ReferenciasYCallbacks.java
// Ejecutar: java Lambdas
import java.util.function.IntUnaryOperator;

public class Lambdas {
    public static int transformar(int valor, IntUnaryOperator funcion) {
        return funcion.applyAsInt(valor);
    }

    public static void main(String[] args) {
        int resultado = transformar(7, numero -> numero * 2);
        System.out.println(resultado);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Callbacks.java Excepciones.java Lambdas.java Receptor.java ReferenciasYCallbacks.java
 *   java Lambdas
 * Salida esperada:
 *   14
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
