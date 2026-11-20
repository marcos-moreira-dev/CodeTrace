// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar/Comprobar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-biblioteca" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: Consulte MainTarifas

package codetrace.practica.tarifas;

/** Dos estrategias intercambiables para el mismo contrato. */
public final class Tarifas {
    private Tarifas() {}

    public static EstrategiaTarifa normal() {
        return subtotal -> subtotal;
    }

    public static EstrategiaTarifa descuento(double fraccion) {
        if (fraccion < 0 || fraccion > 1) throw new IllegalArgumentException("Descuento");
        return subtotal -> subtotal * (1 - fraccion);
    }
}
