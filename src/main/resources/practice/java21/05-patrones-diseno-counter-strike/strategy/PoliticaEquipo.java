// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . GestorEconomia.java MainStrategyCS.java PoliticaCompra.java PoliticaEco.java PoliticaEquipo.java
// Ejecutar: java codetrace.practica.counterstrike.strategy.MainStrategyCS
// Dominio: Counter-Strike — la economía virtual de rondas admite distintas políticas.
// Patrón: Strategy. Cada componente tiene su propia responsabilidad.
// Glosario: Eco = ronda de ahorro de créditos del juego; créditos = dinero virtual de la partida.
package codetrace.practica.counterstrike.strategy;

/** Asignación ficticia de créditos compartidos; no describe armas reales. */
public final class PoliticaEquipo implements PoliticaCompra {
    @Override public String decidir(int creditos) {
        return "Equipo: asignar " + creditos + " créditos virtuales";
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . GestorEconomia.java MainStrategyCS.java PoliticaCompra.java PoliticaEco.java PoliticaEquipo.java
 *   java codetrace.practica.counterstrike.strategy.MainStrategyCS
 * Nota: PoliticaEquipo.java pertenece al ejercicio; el punto de entrada es MainStrategyCS.java.
 * Salida esperada:
 *   Eco: guardar 1500 créditos
 *   Equipo: asignar 1500 créditos virtuales
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
