// Tema:
// main recibe los argumentos de la línea de comandos en args, sin incluir el nombre de la
// clase. args.length cuenta los argumentos y args[i] accede a uno por su posición.
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
