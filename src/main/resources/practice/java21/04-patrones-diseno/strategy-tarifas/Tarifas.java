// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Tarifas de inscripción al torneo según la categoría.
// Patrón strategy-tarifas: Elegir entre reglas de cálculo intercambiables.
// Glosario: Inscripción: registro de un equipo para competir.
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.tarifas.MainTarifas
 * Nota: Tarifas.java es un componente; la salida corresponde al cliente MainTarifas.java.
 * Salida esperada:
 *   100.0
 *   85.0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
