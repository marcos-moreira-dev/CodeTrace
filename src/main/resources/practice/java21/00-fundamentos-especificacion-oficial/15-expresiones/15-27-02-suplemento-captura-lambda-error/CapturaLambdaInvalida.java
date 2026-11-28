// Tema:
// Una variable local capturada por una lambda debe ser final o efectivamente final.
// Reasignar factor impide que cumpla esa condición.
// Compilar: javac --release 21 -encoding UTF-8 -d . CapturaLambdaInvalida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class CapturaLambdaInvalida {
    public static void main(String[] args) {
        int factor = 3;
        java.util.function.IntUnaryOperator multiplicar = n -> n * factor;
        factor = 4;
        System.out.println(multiplicar.applyAsInt(2));
    }
}

// Salida esperada:
// Error: local variables referenced from a lambda expression must be final or effectively final
