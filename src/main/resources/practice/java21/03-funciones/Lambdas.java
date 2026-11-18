import java.util.function.IntUnaryOperator;

public class Lambdas {
    public static int transformar(int valor, IntUnaryOperator funcion) {
        return funcion.applyAsInt(valor);
    }

    public static void main(String[] args) {
        int resultado = transformar(7, numero -> numero * 2);
        System.out.println(resultado);
    }
}
