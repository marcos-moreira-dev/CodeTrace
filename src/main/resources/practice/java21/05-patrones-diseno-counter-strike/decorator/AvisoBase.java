// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . AvisoBase.java AvisoDeRonda.java AvisoEquipo.java AvisoUrgente.java DecoradorAviso.java MainDecoratorCS.java
// Ejecutar: java codetrace.practica.counterstrike.decorator.MainDecoratorCS
// Dominio: Counter-Strike — mensajes del equipo que reciben etiquetas informativas.
// Patrón: Decorator; ejercicio educativo no oficial.
// Glosario: Ronda = etapa breve de partida; rehén = personaje virtual que puede rescatarse.
package codetrace.practica.counterstrike.decorator;

/** Mensaje original, antes de añadirle etiquetas. */
public record AvisoBase(String contenido) implements AvisoEquipo {
    @Override public String texto() { return contenido; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AvisoBase.java AvisoDeRonda.java AvisoEquipo.java AvisoUrgente.java DecoradorAviso.java MainDecoratorCS.java
 *   java codetrace.practica.counterstrike.decorator.MainDecoratorCS
 * Nota: AvisoBase.java pertenece al ejercicio; el cliente es MainDecoratorCS.java.
 * Salida esperada:
 *   [RONDA 12] [URGENTE] Rehén virtual rescatado
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
