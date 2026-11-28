// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Control.java Tipos.java
// Ejecutar: java Tipos
public class Tipos {
    public static void main(String[] args) {
        int unidades = 5;
        double precio = 12.50;
        boolean disponible = true;
        String producto = "Cuaderno";
        double total = unidades * precio;
        System.out.println(producto + ": " + total);
        System.out.println(disponible);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Control.java Tipos.java
 *   java Tipos
 * Salida esperada:
 *   Cuaderno: 62.5
 *   true
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
