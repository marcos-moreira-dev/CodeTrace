// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . AdaptadorPing.java MainAdapterCS.java TelemetriaLegada.java VistaPing.java
// Ejecutar: java codetrace.practica.counterstrike.adapter.MainAdapterCS
// Dominio: Counter-Strike — el servidor de la partida informa la latencia en microsegundos pero el HUD espera milisegundos.
// Patrón: Adapter; ejercicio independiente, no oficial.
// Glosario: Ping = latencia de la conexión; ms = milisegundos; microsegundo = milésima parte de un ms.
package codetrace.practica.counterstrike.adapter;

/** Interfaz que espera la pantalla moderna de Counter-Strike. */
public interface VistaPing { String mostrarPing(); }

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AdaptadorPing.java MainAdapterCS.java TelemetriaLegada.java VistaPing.java
 *   java codetrace.practica.counterstrike.adapter.MainAdapterCS
 * Nota: VistaPing.java se estudia junto al cliente MainAdapterCS.java.
 * Salida esperada:
 *   Ping: 42 ms
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
