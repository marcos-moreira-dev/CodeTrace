// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . ComandoMarcador.java MainCommandCS.java MarcadorPartida.java SumarRondaCt.java
// Ejecutar: java codetrace.practica.counterstrike.command.MainCommandCS
// Dominio: Counter-Strike — el marcador permite registrar y deshacer resultados de ronda.
// Patrón: Command. Cada componente tiene su propia responsabilidad.
// Glosario: CT = equipo antiterrorista; ronda = etapa corta que suma un punto al equipo ganador.
package codetrace.practica.counterstrike.command;

/** Acción reversible: qué ejecutar y cómo deshacerla. */
public interface ComandoMarcador {
    void ejecutar();
    void deshacer();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ComandoMarcador.java MainCommandCS.java MarcadorPartida.java SumarRondaCt.java
 *   java codetrace.practica.counterstrike.command.MainCommandCS
 * Nota: ComandoMarcador.java pertenece al ejercicio; el punto de entrada es MainCommandCS.java.
 * Salida esperada:
 *   Tras ejecutar: CT=1
 *   Tras deshacer: CT=0
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
