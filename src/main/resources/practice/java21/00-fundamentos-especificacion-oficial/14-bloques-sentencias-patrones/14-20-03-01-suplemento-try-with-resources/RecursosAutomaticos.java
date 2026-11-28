// Tema:
// try-with-resources invoca close automáticamente al abandonar el bloque. Si se declaran
// varios recursos, se cierran en orden inverso a su creación.
// Compilar: javac --release 21 -encoding UTF-8 -d . RecursosAutomaticos.java
// Ejecutar: java RecursosAutomaticos
// Comandos desde la carpeta de la unidad.

public class RecursosAutomaticos {
    static class Recurso implements AutoCloseable {
        final String nombre;
        Recurso(String nombre) { this.nombre = nombre; System.out.println("abre " + nombre); }
        public void close() { System.out.println("cierra " + nombre); }
    }
    public static void main(String[] args) {
        try (Recurso a = new Recurso("A"); Recurso b = new Recurso("B")) {
            System.out.println("trabajo");
        }
    }
}

// Salida esperada:
// abre A
// abre B
// trabajo
// cierra B
// cierra A
