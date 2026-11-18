// CodeTrace | Referencia: Java 21 LTS (JDK Temurin 21).
// Desde PowerShell, en una COPIA del paquete: 
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\03-funciones\Lambdas.java'
// Ejecutar: java -cp "$env:TEMP\codetrace-java" Lambdas

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
