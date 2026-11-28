// CodeTrace | Referencia: Java 21 / JDK 21
// Desde la raíz de 05-patrones-diseno-counter-strike.
// Compilar: javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
// Ejecutar: java codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike, simulador narrativo de 3 rondas automáticas.
// Glosario: CT = antiterroristas; T = terroristas; PV = vida del jugador.
// Patrón de organización: infraestructura MVC, no un nuevo patrón GoF.
package codetrace.practica.counterstrike.infraestructura;

import java.util.List;
import java.util.Objects;

/**
 * Agrupa personajes de uno de los bandos de Counter-Strike.
 * List.copyOf conserva una alineación (roster) estable durante la partida;
 * los Jugador que contiene sí pueden cambiar créditos y estadísticas.
 */
public final class Equipo {
    private final Bando bando;
    private final List<Jugador> jugadores;

    public Equipo(Bando bando, List<Jugador> jugadores) {
        this.bando = Objects.requireNonNull(bando, "bando");
        // El equipo mantiene las referencias a los personajes registrados:
        // si Astra compra un objeto, el roster sigue apuntando al mismo Astra.
        this.jugadores = List.copyOf(jugadores);
        // Verificamos que ningún personaje T haya entrado por error al
        // roster CT, o viceversa: sería una simulación contradictoria.
        if (jugadores.isEmpty()
                || jugadores.stream().anyMatch(j -> j.bando() != bando))
            throw new IllegalArgumentException("Plantilla de equipo inválida");
    }

    public Bando bando() { return bando; }
    public List<Jugador> jugadores() { return jugadores; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde la raíz de 05-patrones-diseno-counter-strike:
 *   javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
 *   java codetrace.practica.counterstrike.MainCounterStrike
 * Salida esperada:
 *   Equipos: CT (Astra, Nilo) vs T (Rook, Vega)
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
