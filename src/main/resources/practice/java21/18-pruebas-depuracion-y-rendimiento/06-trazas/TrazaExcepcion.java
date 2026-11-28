// Tema:
// Una traza de pila permite localizar el método que originó una excepción y sus llamadores. Se imprime solo la clase de excepción y nombres de métodos propios, no números de línea dependientes de la edición.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 TrazaExcepcion.java
// Ejecutar: java TrazaExcepcion

public class TrazaExcepcion {
    static void validar() { throw new IllegalStateException("Stock"); }
    static void procesar() { validar(); }

    public static void main(String[] args) throws Exception {
        try { procesar(); }
        catch (IllegalStateException e) {
            System.out.println(e.getClass().getSimpleName());
            for (StackTraceElement marco : e.getStackTrace())
                if (marco.getClassName().equals(TrazaExcepcion.class.getName()))
                    System.out.println(marco.getMethodName());
        }
    }
}

// Salida esperada:
// IllegalStateException
// validar
// procesar
// main
