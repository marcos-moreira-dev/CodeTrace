// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainPuntuacionCS.java ReglaCasual.java ReglaCompetitiva.java ReglaPuntos.java TablaPuntos.java
// Ejecutar: java codetrace.practica.counterstrike.puntuacion.MainPuntuacionCS
// Dominio: Counter-Strike — comparar puntuaciones de torneos casuales y competitivos.
// Patrón: Strategy (variante de tarifas); ejemplo didáctico independiente.
// Glosario: Casual = modo más relajado; competitivo = encuentro con reglas de clasificación; victoria = ronda ganada.
package codetrace.practica.counterstrike.puntuacion;

/** Contrato de algoritmo: el mismo número de victorias da distintos puntos. */
@FunctionalInterface
public interface ReglaPuntos {
    int calcular(int victorias);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainPuntuacionCS.java ReglaCasual.java ReglaCompetitiva.java ReglaPuntos.java TablaPuntos.java
 *   java codetrace.practica.counterstrike.puntuacion.MainPuntuacionCS
 * Nota: ReglaPuntos.java pertenece al ejercicio; MainPuntuacionCS.java es su cliente.
 * Salida esperada:
 *   Casual: 4 puntos
 *   Competitivo: 6 puntos
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
