// Tema:
// Una lambda puede leer variables locales final o efectivamente final. La variable factor
// no cambia después de inicializarse; la lambda puede conservar su valor.
// Compilar: javac --release 21 -encoding UTF-8 -d . CapturaLambda.java
// Ejecutar: java CapturaLambda
// Comandos desde la carpeta de la unidad.

public class CapturaLambda {
    public static void main(String[] args) {
        int factor = 3;
        java.util.function.IntUnaryOperator multiplicar = n -> n * factor;
        System.out.println(multiplicar.applyAsInt(4));
    }
}

// Salida esperada:
// 12
