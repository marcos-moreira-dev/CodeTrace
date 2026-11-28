// Tema:
// null es una referencia que no apunta a ningún objeto. Puede asignarse a tipos de referencia,
// pero no a primitivos. Acceder a un miembro de instancia mediante null provoca una excepción.
// Compilar: javac --release 21 -encoding UTF-8 LiteralNullJls.java
// Ejecutar: java LiteralNullJls
// null representa la ausencia de una referencia a un objeto.

public class LiteralNullJls {
    public static void main(String[] args) {
        String nombre = null;
        System.out.println(nombre == null);
    }
}

// Salida esperada:
// true
