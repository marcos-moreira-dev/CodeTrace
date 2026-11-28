// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . AdaptadorPing.java MainAdapterCS.java TelemetriaLegada.java VistaPing.java
// Ejecutar: java codetrace.practica.counterstrike.adapter.MainAdapterCS
// Dominio: Counter-Strike — el servidor de la partida informa la latencia en microsegundos pero el HUD espera milisegundos.
// Patrón: Adapter; ejercicio independiente, no oficial.
// Glosario: Ping = latencia de la conexión; ms = milisegundos; microsegundo = milésima parte de un ms.
package codetrace.practica.counterstrike.adapter;

/** API antigua: entrega la latencia en microsegundos, no milisegundos. */
public final class TelemetriaLegada {
    public int obtenerPingMicrosegundos() { return 42_000; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AdaptadorPing.java MainAdapterCS.java TelemetriaLegada.java VistaPing.java
 *   java codetrace.practica.counterstrike.adapter.MainAdapterCS
 * Nota: TelemetriaLegada.java se estudia junto al cliente MainAdapterCS.java.
 * Salida esperada:
 *   Ping: 42 ms
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
