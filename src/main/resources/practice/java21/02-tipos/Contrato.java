// CodeTrace | Referencia: Java 21 LTS (JDK Temurin 21).
// Desde PowerShell, en una COPIA del paquete: 
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\02-tipos\Contrato.java'
// Sin punto de entrada main: esta clase se comprueba compilándola.

public interface Contrato {
    double calcular(double subtotal);

    default boolean aplica(double subtotal) {
        return subtotal > 0;
    }
}
