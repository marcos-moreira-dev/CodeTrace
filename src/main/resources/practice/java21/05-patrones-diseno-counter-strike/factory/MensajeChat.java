// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . FabricaMensaje.java MainFactoryCS.java MensajeChat.java MensajeSala.java
// Ejecutar: java codetrace.practica.counterstrike.factory.MainFactoryCS
// Dominio: Counter-Strike — chat de equipo: generar un aviso sin construir su implementación en el cliente.
// Patrón: Simple Factory; ejercicio educativo no oficial.
// Glosario: Chat = canal de mensajes de la partida; ronda = etapa breve del encuentro.
package codetrace.practica.counterstrike.factory;

/** Producto concreto: un mensaje de texto mostrado dentro del juego. */
public record MensajeChat(String contenido) implements MensajeSala {
    @Override public String mostrar() {
        return "Chat de equipo: " + contenido;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . FabricaMensaje.java MainFactoryCS.java MensajeChat.java MensajeSala.java
 *   java codetrace.practica.counterstrike.factory.MainFactoryCS
 * Nota: MensajeChat.java pertenece al ejercicio; el cliente es MainFactoryCS.java.
 * Salida esperada:
 *   Chat de equipo: Ronda iniciada
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
