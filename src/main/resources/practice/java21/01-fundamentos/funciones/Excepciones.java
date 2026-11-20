// CodeTrace | Referencia: Java 21 LTS (JDK Temurin 21).
// Desde PowerShell, en una COPIA del paquete: 
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\03-funciones\Excepciones.java'
// Ejecutar: java -cp "$env:TEMP\codetrace-java" Excepciones

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
