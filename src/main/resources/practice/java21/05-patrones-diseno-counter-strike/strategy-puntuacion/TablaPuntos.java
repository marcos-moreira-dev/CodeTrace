// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainPuntuacionCS.java ReglaCasual.java ReglaCompetitiva.java ReglaPuntos.java TablaPuntos.java
// Ejecutar: java codetrace.practica.counterstrike.puntuacion.MainPuntuacionCS
// Dominio: Counter-Strike — comparar puntuaciones de torneos casuales y competitivos.
// Patrón: Strategy (variante de tarifas); ejemplo didáctico independiente.
// Glosario: Casual = modo más relajado; competitivo = encuentro con reglas de clasificación; victoria = ronda ganada.
package codetrace.practica.counterstrike.puntuacion;

import java.util.Objects;

/** Contexto independiente de las reglas concretas de puntuación. */
public final class TablaPuntos {
    private ReglaPuntos regla;
    public TablaPuntos(ReglaPuntos inicial) { cambiarRegla(inicial); }
    public void cambiarRegla(ReglaPuntos nueva) {
        regla = Objects.requireNonNull(nueva);
    }
    public int puntos(int victorias) {
        if (victorias < 0) throw new IllegalArgumentException("Victorias negativas");
        // La clase no pregunta si la regla es casual o competitiva.
        return regla.calcular(victorias);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainPuntuacionCS.java ReglaCasual.java ReglaCompetitiva.java ReglaPuntos.java TablaPuntos.java
 *   java codetrace.practica.counterstrike.puntuacion.MainPuntuacionCS
 * Nota: TablaPuntos.java pertenece al ejercicio; MainPuntuacionCS.java es su cliente.
 * Salida esperada:
 *   Casual: 4 puntos
 *   Competitivo: 6 puntos
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
