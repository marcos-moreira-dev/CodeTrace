// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §5.1.7
// Compilar: javac --release 21 -encoding UTF-8 -d . Empaquetado.java
// Ejecutar: java Empaquetado
// Comandos desde la carpeta de la unidad.

public class Empaquetado {
    public static void main(String[] args) {
        Integer a = 42;
        Integer b = 42;
        // Para estas constantes pequeñas, la identidad está garantizada.
        System.out.println(a == b);
        System.out.println(a.intValue());
    }
}

// Salida esperada:
// true
// 42
