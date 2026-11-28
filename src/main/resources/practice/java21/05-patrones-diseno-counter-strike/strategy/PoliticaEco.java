// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . GestorEconomia.java MainStrategyCS.java PoliticaCompra.java PoliticaEco.java PoliticaEquipo.java
// Ejecutar: java codetrace.practica.counterstrike.strategy.MainStrategyCS
// Dominio: Counter-Strike — la economía virtual de rondas admite distintas políticas.
// Patrón: Strategy. Cada componente tiene su propia responsabilidad.
// Glosario: Eco = ronda de ahorro de créditos del juego; créditos = dinero virtual de la partida.
package codetrace.practica.counterstrike.strategy;

/** Mantener créditos virtuales para una ronda posterior. */
public final class PoliticaEco implements PoliticaCompra {
    @Override public String decidir(int creditos) {
        return "Eco: guardar " + creditos + " créditos";
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . GestorEconomia.java MainStrategyCS.java PoliticaCompra.java PoliticaEco.java PoliticaEquipo.java
 *   java codetrace.practica.counterstrike.strategy.MainStrategyCS
 * Nota: PoliticaEco.java pertenece al ejercicio; el punto de entrada es MainStrategyCS.java.
 * Salida esperada:
 *   Eco: guardar 1500 créditos
 *   Equipo: asignar 1500 créditos virtuales
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
