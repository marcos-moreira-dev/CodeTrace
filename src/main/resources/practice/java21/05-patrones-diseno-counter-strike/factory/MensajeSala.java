// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . FabricaMensaje.java MainFactoryCS.java MensajeChat.java MensajeSala.java
// Ejecutar: java codetrace.practica.counterstrike.factory.MainFactoryCS
// Dominio: Counter-Strike — chat de equipo: generar un aviso sin construir su implementación en el cliente.
// Patrón: Simple Factory; ejercicio educativo no oficial.
// Glosario: Chat = canal de mensajes de la partida; ronda = etapa breve del encuentro.
package codetrace.practica.counterstrike.factory;

/** Contrato de un mensaje de la sala de Counter-Strike. */
public interface MensajeSala { String mostrar(); }

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . FabricaMensaje.java MainFactoryCS.java MensajeChat.java MensajeSala.java
 *   java codetrace.practica.counterstrike.factory.MainFactoryCS
 * Nota: MensajeSala.java pertenece al ejercicio; el cliente es MainFactoryCS.java.
 * Salida esperada:
 *   Chat de equipo: Ronda iniciada
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
