// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: usar desde el archivo cliente (no contiene main)
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
