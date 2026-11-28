// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Contrato.java Persona.java
// Componente sin punto de entrada; no se ejecuta por separado.
// Sin punto de entrada main: esta clase se comprueba compilándola.

public interface Contrato {
    double calcular(double subtotal);

    default boolean aplica(double subtotal) {
        return subtotal > 0;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Contrato.java Persona.java
 * Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
 * Salida esperada:
 *   (sin salida de consola; la comprobación termina correctamente)
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
