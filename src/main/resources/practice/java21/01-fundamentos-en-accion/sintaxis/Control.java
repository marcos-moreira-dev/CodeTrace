// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
 *   java -cp $out Tipos
 * Nota: Control.java es un componente; la salida corresponde al cliente Tipos.java.
 * Salida esperada:
 *   Cuaderno: 62.5
 *   true
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
