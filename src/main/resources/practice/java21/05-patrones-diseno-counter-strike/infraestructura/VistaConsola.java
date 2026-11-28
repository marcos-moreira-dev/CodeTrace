// Compilar (desde la raíz de 05-patrones-diseno-counter-strike): javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
// Ejecutar: java codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike; narración de una partida ficticia de rescate.
// Glosario: HUD = indicadores de pantalla; CT/T = equipos; PV = puntos de vida.
// Patrón de organización: MVC. Esta VISTA imprime texto sin alterar la partida.
package codetrace.practica.counterstrike.infraestructura;

import java.util.stream.Collectors;

/**
 * VISTA de consola: convierte el estado de la simulación en una narración.
 *
 * No decide quién gana, no actualiza créditos ni elige rehenes. Son tareas
 * del controlador y del modelo. Cambiar aquí la redacción o el formato
 * NO debe cambiar la partida. No hay Scanner ni entrada del usuario.
 */
public final class VistaConsola {
    public void presentar(EstadoPartida partida, long semilla) {
        System.out.println("=== COUNTER-STRIKE | PARTIDA DIDÁCTICA ===");
        System.out.println("Semilla: " + semilla + " (misma semilla = misma secuencia)");
        System.out.println("Mapa: " + partida.mapa().codigo()
                + " | Rescate de rehenes | Rondas: " + partida.configuracion().rondas());
        // Recorremos la lista de jugadores para presentar sus alias; no
        // imprimimos sus objetos directamente (eso daría un toString poco útil).
        String ct = partida.ct().jugadores().stream()
                .map(Jugador::alias).collect(Collectors.joining(", "));
        String t = partida.t().jugadores().stream()
                .map(Jugador::alias).collect(Collectors.joining(", "));
        System.out.println("Equipos: CT (" + ct + ") vs T (" + t + ")");
        System.out.println("Rehenes registrados: " + partida.rehenes().size());
    }

    public void evento(String categoria, String descripcion) {
        // Una etiqueta permite distinguir un aviso de red de uno de economía.
        System.out.println("[" + categoria + "] " + descripcion);
    }

    public void abrirRonda(EstadoPartida partida) {
        System.out.println();
        System.out.println("--- RONDA " + partida.rondaActual()
                + " | Reloj: " + partida.segundosRestantes() + " s ---");
    }

    public void fichaRehen(Rehen rehen) {
        // La consola muestra la ficha del NPC que ya está en memoria.
        // Ubicación significa su lugar de aparición; progreso y estado
        // cuentan si llegó a la extracción. La vista no mueve al personaje.
        evento("REHÉN", rehen.nombre() + " | ubicación=" + rehen.ubicacion()
                + " | calma=" + rehen.calma() + "/100"
                + " | integridad=" + rehen.integridad() + "/100"
                + " | escolta=" + rehen.escolta()
                + " | progreso=" + rehen.progreso() + "%"
                + " | estado=" + rehen.estado());
    }

    public void marcador(EstadoPartida partida) {
        System.out.println("Marcador: CT " + partida.puntosCt()
                + " - " + partida.puntosT() + " T");
    }

    public void cerrarPartida(EstadoPartida partida) {
        System.out.println();
        System.out.println("=== FIN DE LA PARTIDA ===");
        marcador(partida);
        String resultado = partida.puntosCt() == partida.puntosT() ? "Empate"
                : partida.puntosCt() > partida.puntosT() ? "Gana CT" : "Gana T";
        System.out.println("Resultado: " + resultado);

        // Este ciclo recorre el historial, UNA entrada por ronda. El modelo
        // conserva el motivo de cada victoria para poder explicarla después.
        for (String registro : partida.historial())
            System.out.println("  " + registro);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde la raíz de 05-patrones-diseno-counter-strike:
 *   javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
 *   java codetrace.practica.counterstrike.MainCounterStrike
 * Salida esperada:
 *   === COUNTER-STRIKE | PARTIDA DIDÁCTICA ===
 *   Semilla: 42 (misma semilla = misma secuencia)
 *   === FIN DE LA PARTIDA ===
 *   Resultado: Gana CT
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
