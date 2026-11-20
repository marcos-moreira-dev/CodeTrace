// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: ejecutar desde el archivo cliente del mismo ejercicio

package codetrace.practica.adapter;

import java.util.Objects;

/**
 * Adaptador de una interfaz externa a una interfaz del dominio.
 * No modifica la API del proveedor; convierte unidades antes de exponerlas.
 */
public final class AdaptadorTemperatura implements SensorCelsius {
    private final TermometroLegado proveedor;

    public AdaptadorTemperatura(TermometroLegado proveedor) {
        this.proveedor = Objects.requireNonNull(proveedor);
    }

    @Override
    public double leerCelsius() {
        return (proveedor.lecturaFahrenheit() - 32.0) * (5.0 / 9.0);
    }
}
