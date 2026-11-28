// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Callbacks.java Excepciones.java Lambdas.java Receptor.java ReferenciasYCallbacks.java
// Ejecutar: java Callbacks
// Salida esperada: Hola, seguido de Lista.
/** Interfaz funcional para recibir mensajes como callback. */
@FunctionalInterface
public interface Receptor {
    void recibir(String mensaje);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Callbacks.java Excepciones.java Lambdas.java Receptor.java ReferenciasYCallbacks.java
 *   java Callbacks
 * Nota: Receptor.java es un componente; la salida corresponde al cliente Callbacks.java.
 * Salida esperada:
 *   Hola
 *   Lista
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
