// Tema:
// Al envolver una excepción, conservar la causa permite conocer el contexto de negocio y el origen técnico. No ocultamos la causa ni imprimimos datos sensibles.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 CausasExcepcion.java
// Ejecutar: java CausasExcepcion

public class CausasExcepcion {
    static void cargar() {
        try { Integer.parseInt("no-es-numero"); }
        catch (NumberFormatException e) { throw new IllegalStateException("No se pudo cargar", e); }
    }

    public static void main(String[] args) throws Exception {
        try { cargar(); }
        catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause().getClass().getSimpleName());
        }
    }
}

// Salida esperada:
// No se pudo cargar
// NumberFormatException
