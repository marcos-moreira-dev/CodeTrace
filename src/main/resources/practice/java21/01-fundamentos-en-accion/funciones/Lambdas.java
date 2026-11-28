// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
 *   java -cp $out Lambdas
 * Salida esperada:
 *   14
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
