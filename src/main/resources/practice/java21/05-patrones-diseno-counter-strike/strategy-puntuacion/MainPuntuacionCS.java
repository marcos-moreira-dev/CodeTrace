// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . MainPuntuacionCS.java ReglaCasual.java ReglaCompetitiva.java ReglaPuntos.java TablaPuntos.java
// Ejecutar: java codetrace.practica.counterstrike.puntuacion.MainPuntuacionCS
// Dominio: Counter-Strike — comparar puntuaciones de torneos casuales y competitivos.
// Patrón: Strategy (variante de tarifas); ejemplo didáctico independiente.
// Glosario: Casual = modo más relajado; competitivo = encuentro con reglas de clasificación; victoria = ronda ganada.
package codetrace.practica.counterstrike.puntuacion;

/** Cliente: intercambia reglas conservando la misma cantidad de victorias. */
public final class MainPuntuacionCS {
    public static void main(String[] args) {
        TablaPuntos tabla = new TablaPuntos(new ReglaCasual());
        int victorias = 2; // Valor ficticio, no resultado real de Counter-Strike.

        System.out.println("Casual: " + tabla.puntos(victorias) + " puntos");
        tabla.cambiarRegla(new ReglaCompetitiva());
        System.out.println("Competitivo: " + tabla.puntos(victorias) + " puntos");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainPuntuacionCS.java ReglaCasual.java ReglaCompetitiva.java ReglaPuntos.java TablaPuntos.java
 *   java codetrace.practica.counterstrike.puntuacion.MainPuntuacionCS
 * Nota: MainPuntuacionCS.java pertenece al ejercicio; MainPuntuacionCS.java es su cliente.
 * Salida esperada:
 *   Casual: 4 puntos
 *   Competitivo: 6 puntos
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
