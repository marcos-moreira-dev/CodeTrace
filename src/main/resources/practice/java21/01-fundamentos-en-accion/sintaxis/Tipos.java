// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" Tipos
public class Tipos {
    public static void main(String[] args) {
        int unidades = 5;
        double precio = 12.50;
        boolean disponible = true;
        String producto = "Cuaderno";
        double total = unidades * precio;
        System.out.println(producto + ": " + total);
        System.out.println(disponible);
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
 * Salida esperada:
 *   Cuaderno: 62.5
 *   true
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
