// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . EstrategiaTarifa.java MainTarifas.java Tarifas.java
// Ejecutar: java codetrace.practica.tarifas.MainTarifas
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Tarifas de inscripción al torneo según la categoría.
// Patrón strategy-tarifas: Cambiar la política de cálculo sin modificar el cliente.
// Glosario: Inscripción: registro de un participante o equipo al torneo.
package codetrace.practica.tarifas;

/** Contrato del cálculo, independiente de quien lo utilice. */
@FunctionalInterface
public interface EstrategiaTarifa {
    double total(double subtotal);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . EstrategiaTarifa.java MainTarifas.java Tarifas.java
 *   java codetrace.practica.tarifas.MainTarifas
 * Nota: EstrategiaTarifa.java es un componente; la salida corresponde al cliente MainTarifas.java.
 * Salida esperada:
 *   100.0
 *   85.0
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
