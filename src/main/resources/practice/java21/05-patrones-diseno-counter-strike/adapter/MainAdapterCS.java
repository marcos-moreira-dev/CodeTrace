// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . AdaptadorPing.java MainAdapterCS.java TelemetriaLegada.java VistaPing.java
// Ejecutar: java codetrace.practica.counterstrike.adapter.MainAdapterCS
// Dominio: Counter-Strike — el servidor de la partida informa la latencia en microsegundos pero el HUD espera milisegundos.
// Patrón: Adapter; ejercicio independiente, no oficial.
// Glosario: Ping = latencia de la conexión; ms = milisegundos; microsegundo = milésima parte de un ms.
package codetrace.practica.counterstrike.adapter;

/** Cliente: solo conoce VistaPing, no necesita saber la unidad legada. */
public final class MainAdapterCS {
    public static void main(String[] args) {
        TelemetriaLegada servidor = new TelemetriaLegada();
        VistaPing hud = new AdaptadorPing(servidor);
        // El adaptador permite usar la API antigua sin cambiar el HUD.
        System.out.println(hud.mostrarPing());
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AdaptadorPing.java MainAdapterCS.java TelemetriaLegada.java VistaPing.java
 *   java codetrace.practica.counterstrike.adapter.MainAdapterCS
 * Nota: MainAdapterCS.java se estudia junto al cliente MainAdapterCS.java.
 * Salida esperada:
 *   Ping: 42 ms
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
