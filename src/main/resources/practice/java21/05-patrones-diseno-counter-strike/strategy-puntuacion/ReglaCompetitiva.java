// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainPuntuacionCS.java ReglaCasual.java ReglaCompetitiva.java ReglaPuntos.java TablaPuntos.java
// Ejecutar: java codetrace.practica.counterstrike.puntuacion.MainPuntuacionCS
// Dominio: Counter-Strike — comparar puntuaciones de torneos casuales y competitivos.
// Patrón: Strategy (variante de tarifas); ejemplo didáctico independiente.
// Glosario: Casual = modo más relajado; competitivo = encuentro con reglas de clasificación; victoria = ronda ganada.
package codetrace.practica.counterstrike.puntuacion;

/** En este torneo ficticio cada victoria competitiva vale 3 puntos. */
public final class ReglaCompetitiva implements ReglaPuntos {
    @Override public int calcular(int victorias) { return victorias * 3; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainPuntuacionCS.java ReglaCasual.java ReglaCompetitiva.java ReglaPuntos.java TablaPuntos.java
 *   java codetrace.practica.counterstrike.puntuacion.MainPuntuacionCS
 * Nota: ReglaCompetitiva.java pertenece al ejercicio; MainPuntuacionCS.java es su cliente.
 * Salida esperada:
 *   Casual: 4 puntos
 *   Competitivo: 6 puntos
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
