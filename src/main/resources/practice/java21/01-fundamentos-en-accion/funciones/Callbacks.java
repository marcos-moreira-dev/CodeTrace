// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Callbacks.java Excepciones.java Lambdas.java Receptor.java ReferenciasYCallbacks.java
// Ejecutar: java Callbacks
public final class Callbacks {
    private Callbacks() {}

    static void notificar(String texto, Receptor receptor) {
        receptor.recibir(texto);
    }

    static void mostrar(String texto) {
        System.out.println(texto);
    }

    public static void main(String[] args) {
        notificar("Hola", Callbacks::mostrar);

        Runnable tarea = () -> mostrar("Lista");
        tarea.run();
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Callbacks.java Excepciones.java Lambdas.java Receptor.java ReferenciasYCallbacks.java
 *   java Callbacks
 * Salida esperada:
 *   Hola
 *   Lista
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
