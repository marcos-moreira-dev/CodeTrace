// Tema:
// Los parámetros de una lambda pueden tener tipos explícitos, inferidos o var de manera
// consistente. La interfaz funcional objetivo determina los tipos de sus parámetros y su
// retorno.
// Compilar: javac --release 21 -encoding UTF-8 -d . ParametrosLambda.java
// Ejecutar: java ParametrosLambda
// Comandos desde la carpeta de la unidad.

public class ParametrosLambda {
    public static void main(String[] args) {
        java.util.function.IntUnaryOperator a = x -> x + 1;
        java.util.function.IntBinaryOperator b = (int x, int y) -> x + y;
        java.util.function.IntBinaryOperator c = (var x, var y) -> x * y;
        System.out.println(a.applyAsInt(3));
        System.out.println(b.applyAsInt(2, 5));
        System.out.println(c.applyAsInt(2, 5));
    }
}

// Salida esperada:
// 4
// 7
// 10
