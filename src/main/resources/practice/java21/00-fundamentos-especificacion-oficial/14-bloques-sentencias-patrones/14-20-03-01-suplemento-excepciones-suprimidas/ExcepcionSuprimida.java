// Tema:
// Si el cuerpo de try y close lanzan excepciones, la del cuerpo se conserva como principal.
// La excepción de cierre se agrega mediante getSuppressed.
// Compilar: javac --release 21 -encoding UTF-8 -d . ExcepcionSuprimida.java
// Ejecutar: java ExcepcionSuprimida
// Comandos desde la carpeta de la unidad.

public class ExcepcionSuprimida {
    static class Recurso implements AutoCloseable {
        public void close() { throw new IllegalStateException("cierre"); }
    }
    public static void main(String[] args) {
        try (Recurso r = new Recurso()) {
            throw new IllegalArgumentException("cuerpo");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSuppressed()[0].getMessage());
        }
    }
}

// Salida esperada:
// cuerpo
// cierre
