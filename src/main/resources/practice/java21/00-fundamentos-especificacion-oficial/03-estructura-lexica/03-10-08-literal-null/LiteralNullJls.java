// CodeTrace | Fundamentos - Especificacion oficial
// Referencia: Java SE 21, JLS §3.10.8
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
