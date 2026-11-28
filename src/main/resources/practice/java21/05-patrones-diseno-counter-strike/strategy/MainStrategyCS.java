// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . GestorEconomia.java MainStrategyCS.java PoliticaCompra.java PoliticaEco.java PoliticaEquipo.java
// Ejecutar: java codetrace.practica.counterstrike.strategy.MainStrategyCS
// Dominio: Counter-Strike — la economía virtual de rondas admite distintas políticas.
// Patrón: Strategy. Cada componente tiene su propia responsabilidad.
// Glosario: Eco = ronda de ahorro de créditos del juego; créditos = dinero virtual de la partida.
package codetrace.practica.counterstrike.strategy;

/** Cliente: elige una estrategia y la cambia durante la demostración. */
public final class MainStrategyCS {
    public static void main(String[] args) {
        GestorEconomia economia = new GestorEconomia(new PoliticaEco());
        // Misma cantidad de créditos, resultado diferente según la estrategia.
        System.out.println(economia.plan(1500));
        economia.cambiarPolitica(new PoliticaEquipo());
        System.out.println(economia.plan(1500));
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . GestorEconomia.java MainStrategyCS.java PoliticaCompra.java PoliticaEco.java PoliticaEquipo.java
 *   java codetrace.practica.counterstrike.strategy.MainStrategyCS
 * Nota: MainStrategyCS.java pertenece al ejercicio; el punto de entrada es MainStrategyCS.java.
 * Salida esperada:
 *   Eco: guardar 1500 créditos
 *   Equipo: asignar 1500 créditos virtuales
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
