// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . EstrategiaTarifa.java MainTarifas.java Tarifas.java
// Ejecutar: java codetrace.practica.tarifas.MainTarifas
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Tarifas de inscripción al torneo según la categoría.
// Patrón strategy-tarifas: Elegir entre reglas de cálculo intercambiables.
// Glosario: Inscripción: registro de un equipo para competir.
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
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . EstrategiaTarifa.java MainTarifas.java Tarifas.java
 *   java codetrace.practica.tarifas.MainTarifas
 * Nota: Tarifas.java es un componente; la salida corresponde al cliente MainTarifas.java.
 * Salida esperada:
 *   100.0
 *   85.0
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
