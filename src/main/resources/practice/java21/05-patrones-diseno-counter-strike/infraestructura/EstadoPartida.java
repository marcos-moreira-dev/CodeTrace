// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Dominio: Counter-Strike, simulador narrativo de 3 rondas automáticas.
// Glosario: CT = antiterroristas; T = terroristas; PV = vida del jugador.
// Patrón de organización: infraestructura MVC, no un nuevo patrón GoF.
package codetrace.practica.counterstrike.infraestructura;

import codetrace.practica.counterstrike.builder.ConfiguracionSala;
import codetrace.practica.counterstrike.command.MarcadorPartida;
import codetrace.practica.counterstrike.command.SumarRondaCt;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * MODELO de MVC: el lugar donde realmente viven los datos.
 *
 * El controlador decide qué suceso ocurre, esta clase registra sus
 * consecuencias y la vista solo consulta el resultado. De esa forma,
 * cambiar los mensajes de consola no modifica las reglas del marcador.
 */
public final class EstadoPartida {
    private final ConfiguracionSala configuracion;
    private final MapaPartida mapa;
    private final Equipo ct;
    private final Equipo t;
    private final List<Rehen> rehenes;
    private final List<String> historial = new ArrayList<>();
    private final MarcadorPartida marcadorCt = new MarcadorPartida();
    private int puntosT;
    private int rondaActual;
    private int segundosRestantes;

    public EstadoPartida(ConfiguracionSala configuracion, MapaPartida mapa,
                         Equipo ct, Equipo t, List<Rehen> rehenes) {
        this.configuracion = Objects.requireNonNull(configuracion);
        this.mapa = Objects.requireNonNull(mapa);
        this.ct = Objects.requireNonNull(ct);
        this.t = Objects.requireNonNull(t);
        this.rehenes = List.copyOf(rehenes);
        if (ct.bando() != Bando.CT || t.bando() != Bando.T || rehenes.isEmpty())
            throw new IllegalArgumentException("Partida mal configurada");
    }

    public ConfiguracionSala configuracion() { return configuracion; }
    public MapaPartida mapa() { return mapa; }
    public Equipo ct() { return ct; }
    public Equipo t() { return t; }
    public List<Rehen> rehenes() { return rehenes; }
    public int rondaActual() { return rondaActual; }
    public int segundosRestantes() { return segundosRestantes; }
    public int puntosCt() { return marcadorCt.rondasCt(); }
    public int puntosT() { return puntosT; }
    public List<String> historial() { return List.copyOf(historial); }

    public void iniciarRonda(int numero) {
        if (numero != rondaActual + 1 || numero > configuracion.rondas())
            throw new IllegalArgumentException("Número de ronda incorrecto");
        rondaActual = numero;
        segundosRestantes = mapa.duracionRondaSegundos();

        // El ciclo prepara CADA rehén y jugador, no solo al protagonista.
        // La misión y los puntos de vida se reinician por ronda, pero el
        // marcador total, los créditos y el historial deben continuar.
        for (Rehen rehen : rehenes) rehen.reiniciar();
        for (Jugador jugador : ct.jugadores()) jugador.prepararRonda();
        for (Jugador jugador : t.jugadores()) jugador.prepararRonda();
    }

    public void avanzarReloj(int segundos) {
        if (segundos < 0) throw new IllegalArgumentException("Tiempo negativo");
        segundosRestantes = Math.max(0, segundosRestantes - segundos);
    }

    public void registrarVictoria(Bando ganador, String motivo) {
        Objects.requireNonNull(ganador, "ganador");
        if (motivo == null || motivo.isBlank())
            throw new IllegalArgumentException("Falta motivo de victoria");
        if (rondaActual == 0 || historial.size() >= rondaActual)
            throw new IllegalStateException("Ronda inexistente o ya registrada");

        if (ganador == Bando.CT) {
            // Reutilizamos un COMANDO existente para modificar el marcador CT.
            // La operación podría deshacerse sin cambiar esta clase.
            new SumarRondaCt(marcadorCt).ejecutar();
        } else {
            puntosT++; // El contador T es directo: aquí no necesitamos deshacer.
        }
        historial.add("Ronda " + rondaActual + ": "
                + ganador.name() + " — " + motivo);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.MainCounterStrike
 * La victoria CT usa Command; la victoria T incrementa su contador independiente.
 * Salida esperada:
 *   Marcador: CT 1 - 0 T
 *   Marcador: CT 1 - 1 T
 *   Marcador: CT 2 - 1 T
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
