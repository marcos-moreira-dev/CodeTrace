// Tema:
// Una cláusula throws puede utilizar una variable de tipo acotada por Throwable. El argumento
// de tipo permite determinar qué excepción debe manejar el cliente.
// Compilar: javac --release 21 -encoding UTF-8 -d . ExcepcionGenerica.java
// Ejecutar: java ExcepcionGenerica
// Comandos desde la carpeta de la unidad.

public class ExcepcionGenerica {
    static <E extends Exception> void lanzar(E error) throws E { throw error; }
    public static void main(String[] args) {
        try { lanzar(new java.io.IOException("lectura")); }
        catch (java.io.IOException e) { System.out.println(e.getMessage()); }
    }
}

// Salida esperada:
// lectura
