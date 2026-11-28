// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . GestorEconomia.java MainStrategyCS.java PoliticaCompra.java PoliticaEco.java PoliticaEquipo.java
// Ejecutar: java codetrace.practica.counterstrike.strategy.MainStrategyCS
// Dominio: Counter-Strike — la economía virtual de rondas admite distintas políticas.
// Patrón: Strategy. Cada componente tiene su propia responsabilidad.
// Glosario: Eco = ronda de ahorro de créditos del juego; créditos = dinero virtual de la partida.
package codetrace.practica.counterstrike.strategy;

import java.util.Objects;

/** Contexto: ejecuta la estrategia sin saber cómo se calcula su respuesta. */
public final class GestorEconomia {
    private PoliticaCompra politica;

    public GestorEconomia(PoliticaCompra inicial) {
        cambiarPolitica(inicial);
    }

    public void cambiarPolitica(PoliticaCompra nueva) {
        politica = Objects.requireNonNull(nueva);
    }

    public String plan(int creditos) {
        if (creditos < 0) throw new IllegalArgumentException("Créditos negativos");
        return politica.decidir(creditos);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . GestorEconomia.java MainStrategyCS.java PoliticaCompra.java PoliticaEco.java PoliticaEquipo.java
 *   java codetrace.practica.counterstrike.strategy.MainStrategyCS
 * Nota: GestorEconomia.java pertenece al ejercicio; el punto de entrada es MainStrategyCS.java.
 * Salida esperada:
 *   Eco: guardar 1500 créditos
 *   Equipo: asignar 1500 créditos virtuales
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
