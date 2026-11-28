// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . ComandoMarcador.java MainCommandCS.java MarcadorPartida.java SumarRondaCt.java
// Ejecutar: java codetrace.practica.counterstrike.command.MainCommandCS
// Dominio: Counter-Strike — el marcador permite registrar y deshacer resultados de ronda.
// Patrón: Command. Cada componente tiene su propia responsabilidad.
// Glosario: CT = equipo antiterrorista; ronda = etapa corta que suma un punto al equipo ganador.
package codetrace.practica.counterstrike.command;

/** Receptor: posee el estado verdadero del marcador de la partida. */
public final class MarcadorPartida {
    private int rondasCt;

    public void sumarCt() { rondasCt++; }

    public void restarCt() {
        // Evitamos que un comando borre un punto que nunca se registró.
        if (rondasCt == 0) throw new IllegalStateException("Marcador vacío");
        rondasCt--;
    }

    public int rondasCt() { return rondasCt; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ComandoMarcador.java MainCommandCS.java MarcadorPartida.java SumarRondaCt.java
 *   java codetrace.practica.counterstrike.command.MainCommandCS
 * Nota: MarcadorPartida.java pertenece al ejercicio; el punto de entrada es MainCommandCS.java.
 * Salida esperada:
 *   Tras ejecutar: CT=1
 *   Tras deshacer: CT=0
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
