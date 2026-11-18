// CodeTrace | Referencia: Java 21 LTS (JDK Temurin 21).
// Desde PowerShell, en una COPIA del paquete: 
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\01-sintaxis\Control.java'
// Sin punto de entrada main: esta clase se comprueba compilándola.

public class Control {
    public static int sumarPares(int limite) {
        int total = 0;
        for (int i = 0; i <= limite; i++) {
            if (i % 2 == 0) {
                total += i;
            }
        }
        return total;
    }
}
