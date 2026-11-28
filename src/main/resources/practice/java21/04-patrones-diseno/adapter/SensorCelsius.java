// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . AdaptadorTemperatura.java MainAdapter.java SensorCelsius.java TermometroLegado.java
// Ejecutar: java codetrace.practica.adapter.MainAdapter
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Termómetro antiguo del servidor que aloja partidas.
// Patrón adapter: Convertir una lectura antigua a la interfaz esperada.
// Glosario: Servidor de partida: computadora que coordina el juego.

package codetrace.practica.adapter;

/** Contrato que espera el código cliente, independiente del proveedor. */
public interface SensorCelsius {
    double leerCelsius();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AdaptadorTemperatura.java MainAdapter.java SensorCelsius.java TermometroLegado.java
 *   java codetrace.practica.adapter.MainAdapter
 * Nota: SensorCelsius.java es un componente; la salida corresponde al cliente MainAdapter.java.
 * Salida esperada:
 *   Temperatura: 25.0 °C
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
