// CodeTrace | Fundamentos - Especificacion oficial
// Referencia: Java SE 21, JLS §1.2
// Compilar: javac --release 21 -encoding UTF-8 ArgumentosJls.java
// Ejecutar: java ArgumentosJls Java 21
// Los argumentos de main llegan como un arreglo de cadenas.

public class ArgumentosJls {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(args[i]);
        }
        System.out.println();
    }
}

// Salida esperada:
// Java 21
