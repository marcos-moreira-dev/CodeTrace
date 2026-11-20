// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" Excepciones
public class Excepciones {
    public static int dividir(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisor cero");
        }
        return a / b;
    }

    public static void main(String[] args) {
        try {
            System.out.println(dividir(10, 0));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
