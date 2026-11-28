// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 MainRecursosYSuppressed.java RecursoDemostrativo.java
// Ejecutar: java MainRecursosYSuppressed

/**
 * Recurso simulado que implementa AutoCloseable.
 *
 * Permite estudiar que close() se ejecuta incluso si el cuerpo del
 * bloque falla. La excepción de cierre se almacena como suprimida
 * cuando ya existía un fallo principal. No abre archivos reales.
 */
public final class RecursoDemostrativo implements AutoCloseable {
    private final String nombre;
    public RecursoDemostrativo(String nombre) {
        this.nombre = nombre;
        System.out.println("Abrir: " + nombre);
    }
    public void procesar() {
        throw new IllegalStateException("Error del proceso " + nombre);
    }
    @Override public void close() {
        System.out.println("Cerrar: " + nombre);
        throw new IllegalStateException("Error al cerrar " + nombre);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 MainRecursosYSuppressed.java RecursoDemostrativo.java
 *   java MainRecursosYSuppressed
 * Nota: RecursoDemostrativo.java es un componente; la salida corresponde al cliente MainRecursosYSuppressed.java.
 * Salida esperada:
 *   Abrir: demostración
 *   Cerrar: demostración
 *   Principal: Error del proceso demostración
 *   Suprimidas: 1
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
