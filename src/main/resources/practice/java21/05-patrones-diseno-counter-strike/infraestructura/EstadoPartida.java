// Compilar (desde la raíz de 05-patrones-diseno-counter-strike): javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
// Ejecutar: java codetrace.practica.counterstrike.MainCounterStrike
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
        // Conservamos la relación de NPC asignados al mapa. List.copyOf
        // impide añadir o retirar objetivos desde fuera, pero los Rehen
        // que contiene son las mismas referencias mutables de la partida.
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

        // Como se reinicia el escenario al comenzar la ronda siguiente,
        // recorremos TODOS los NPC registrados y restauramos cada objetivo.
        // El historial de rescates anteriores no se borra: pertenece a
        // la partida completa, igual que el marcador y los créditos.
        for (Rehen rehen : rehenes) rehen.reiniciar();

        // Los jugadores también recuperan sus atributos iniciales de ronda.
        // Sus inventarios y créditos se conservan en este ejemplo didáctico.
        for (Jugador jugador : ct.jugadores()) jugador.prepararRonda();
        for (Jugador jugador : t.jugadores()) jugador.prepararRonda();
    }

    public void avanzarReloj(int segundos) {
        if (segundos < 0) throw new IllegalArgumentException("Tiempo negativo");
        // El HUD no puede mostrar tiempo negativo cuando termina la ronda.
        // Por ello limitamos el reloj a cero tras descontar el tiempo.
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
        // Guardamos una descripción inmutable del resultado (por ejemplo,
        // rescate de Elena), no una referencia a un Rehen que se reiniciará.
        // Así el resumen final conserva el acontecimiento de cada ronda.
        historial.add("Ronda " + rondaActual + ": "
                + ganador.name() + " — " + motivo);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde la raíz de 05-patrones-diseno-counter-strike:
 *   javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
 *   java codetrace.practica.counterstrike.MainCounterStrike
 * Salida esperada:
 *   Marcador: CT 1 - 0 T
 *   Marcador: CT 1 - 1 T
 *   Marcador: CT 2 - 1 T
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
