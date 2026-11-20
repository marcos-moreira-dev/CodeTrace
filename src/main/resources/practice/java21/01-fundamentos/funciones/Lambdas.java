// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" Lambdas
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
