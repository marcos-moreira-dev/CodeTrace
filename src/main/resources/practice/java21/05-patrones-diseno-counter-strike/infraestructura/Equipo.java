// CodeTrace | Referencia: Java 21 LTS / JDK 21
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
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.MainCounterStrike
 * La vista recorre las listas de jugadores para construir las alineaciones CT y T.
 * Salida esperada:
 *   Equipos: CT (Astra, Nilo) vs T (Rook, Vega)
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
