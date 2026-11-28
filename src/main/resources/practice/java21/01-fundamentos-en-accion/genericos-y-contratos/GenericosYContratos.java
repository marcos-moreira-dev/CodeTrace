// Compilar (desde esta carpeta): javac -encoding UTF-8 GenericosYContratos.java
// Ejecutar: java GenericosYContratos

import java.util.ArrayList;
import java.util.List;

/**
 * Cliente de genéricos, límites y comodines.
 *
 * <p>T limita los valores aceptados y elimina conversiones innecesarias.
 * Un productor puede exponerse como List<? extends Number> para lectura;
 * un receptor List<? super Integer> admite insertar Integer. Esto
 * ejemplifica PECS (Producer Extends, Consumer Super), sin desarrollar
 * una jerarquía que no ayude al ejemplo.
 */
public class GenericosYContratos {
    static double sumar(List<? extends Number> fuente) {
        double total = 0;
        for (Number n : fuente) total += n.doubleValue();
        return total;
    }

    static void agregarEnteros(List<? super Integer> destino, int... valores) {
        for (int valor : valores) destino.add(valor);
    }

    public static void main(String[] args) {
        List<Number> numeros = new ArrayList<>();
        agregarEnteros(numeros, 2, 3, 5);
        System.out.println("Total: " + sumar(numeros));
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 GenericosYContratos.java
 *   java GenericosYContratos
 * Salida esperada:
 *   Total: 10.0
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
