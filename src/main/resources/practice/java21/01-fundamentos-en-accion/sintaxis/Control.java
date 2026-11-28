// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Control.java Tipos.java
// Ejecutar: java Tipos
// Sin punto de entrada main: esta clase se comprueba compilándola.

public class Control {
    public static int sumarPares(int limite) {
        int total = 0;
        for (int i = 0; i <= limite; i++) {
            if (i % 2 == 0) {
                total += i;
            }
        }
        return total;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Control.java Tipos.java
 *   java Tipos
 * Nota: Control.java es un componente; la salida corresponde al cliente Tipos.java.
 * Salida esperada:
 *   Cuaderno: 62.5
 *   true
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
