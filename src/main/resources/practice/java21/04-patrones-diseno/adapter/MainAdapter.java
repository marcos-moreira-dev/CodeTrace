// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.adapter.MainAdapter

package codetrace.practica.adapter;

/**
 * Cliente del patrón Adapter: traducir contratos incompatibles.
 *
 * <p>Una aplicación necesita grados Celsius, pero un proveedor legado
 * solo expone Fahrenheit. La alternativa de modificar cada cliente
 * acopla todo el código al proveedor. Adapter encapsula la conversión.
 *
 * <p>Roles: SensorCelsius = contrato objetivo; TermometroLegado =
 * proveedor existente; AdaptadorTemperatura = traductor; este archivo
 * utiliza solo el contrato objetivo una vez construido el adaptador.
 */
public final class MainAdapter {
    static void mostrar(SensorCelsius sensor) {
        System.out.printf("Temperatura: %.1f °C%n", sensor.leerCelsius());
    }

    public static void main(String[] args) {
        SensorCelsius sensor = new AdaptadorTemperatura(new TermometroLegado(77.0));
        mostrar(sensor);
    }
}
