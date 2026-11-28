// Compilar (desde esta carpeta): javac -encoding UTF-8 MainRecursosYSuppressed.java RecursoDemostrativo.java
// Ejecutar: java MainRecursosYSuppressed

/**
 * Cliente: try-with-resources y excepciones suprimidas.
 *
 * <p>La forma {@code try (recurso)} garantiza la llamada a close() al
 * salir del ámbito. Si procesar() falla y close() también falla,
 * Java conserva el primer error como principal y el segundo mediante
 * {@code Throwable.getSuppressed()} para no perder información.
 *
 * <p>Separamos recurso y cliente porque resulta importante reconocer
 * el contrato AutoCloseable y el código que gestiona su ciclo de vida.
 * En un sistema real usaríamos streams, conexiones o archivos en lugar
 * de un recurso que falla deliberadamente.
 */
public final class MainRecursosYSuppressed {
    public static void main(String[] args) {
        try (RecursoDemostrativo recurso = new RecursoDemostrativo("demostración")) {
            recurso.procesar();
        } catch (IllegalStateException ex) {
            System.out.println("Principal: " + ex.getMessage());
            System.out.println("Suprimidas: " + ex.getSuppressed().length);
            if (ex.getSuppressed().length != 1)
                throw new AssertionError("Se perdió el fallo de cierre");
        }
    }
}

/*
 * Salida esperada de consola (la apertura y el cierre también imprimen):
 *   Abrir: demostración
 *   Cerrar: demostración
 *   Principal: Error del proceso demostración
 *   Suprimidas: 1
 * close() falla, pero su excepción queda asociada a la principal.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 MainRecursosYSuppressed.java RecursoDemostrativo.java
 *   java MainRecursosYSuppressed
 * Salida esperada:
 *   Abrir: demostración
 *   Cerrar: demostración
 *   Principal: Error del proceso demostración
 *   Suprimidas: 1
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
