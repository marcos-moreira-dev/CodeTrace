// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: ejecutar desde el archivo cliente del mismo ejercicio

package codetrace.practica.adapter;

/**
 * API heredada hipotética: su unidad es Fahrenheit, no Celsius.
 * Simula un proveedor cuyo contrato no se puede cambiar.
 */
public final class TermometroLegado {
    private final double fahrenheit;

    public TermometroLegado(double fahrenheit) {
        this.fahrenheit = fahrenheit;
    }

    public double lecturaFahrenheit() { return fahrenheit; }
}
