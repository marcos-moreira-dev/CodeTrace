// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . AvisoBase.java AvisoDeRonda.java AvisoEquipo.java AvisoUrgente.java DecoradorAviso.java MainDecoratorCS.java
// Ejecutar: java codetrace.practica.counterstrike.decorator.MainDecoratorCS
// Dominio: Counter-Strike — mensajes del equipo que reciben etiquetas informativas.
// Patrón: Decorator; ejercicio educativo no oficial.
// Glosario: Ronda = etapa breve de partida; rehén = personaje virtual que puede rescatarse.
package codetrace.practica.counterstrike.decorator;

/** Agrega la etiqueta URGENTE sin modificar AvisoBase. */
public final class AvisoUrgente extends DecoradorAviso {
    public AvisoUrgente(AvisoEquipo aviso) { super(aviso); }
    @Override public String texto() {
        return "[URGENTE] " + envuelto.texto();
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AvisoBase.java AvisoDeRonda.java AvisoEquipo.java AvisoUrgente.java DecoradorAviso.java MainDecoratorCS.java
 *   java codetrace.practica.counterstrike.decorator.MainDecoratorCS
 * Nota: AvisoUrgente.java pertenece al ejercicio; el cliente es MainDecoratorCS.java.
 * Salida esperada:
 *   [RONDA 12] [URGENTE] Rehén virtual rescatado
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
