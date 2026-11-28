// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . AdaptadorTemperatura.java MainAdapter.java SensorCelsius.java TermometroLegado.java
// Ejecutar: java codetrace.practica.adapter.MainAdapter
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Termómetro antiguo del servidor que aloja partidas.
// Patrón adapter: Convertir una lectura antigua a la interfaz esperada.
// Glosario: Servidor de partida: computadora que coordina el juego.

package codetrace.practica.adapter;

/**
 * Cliente del patrón Adapter: traducir contratos incompatibles.
 *
 * <p>Una aplicación necesita grados Celsius, pero un proveedor legado
 * solo expone Fahrenheit. La alternativa de modificar cada cliente
 * acopla todo el código al proveedor. Adapter encapsula la conversión.
 *
 * <p>Roles: SensorCelsius = contrato objetivo; TermometroLegado =
 * proveedor existente; AdaptadorTemperatura = traductor; este archivo
 * utiliza solo el contrato objetivo una vez construido el adaptador.
 */
public final class MainAdapter {
    static void mostrar(SensorCelsius sensor) {
        System.out.printf("Temperatura: %.1f °C%n", sensor.leerCelsius());
    }

    public static void main(String[] args) {
        SensorCelsius sensor = new AdaptadorTemperatura(new TermometroLegado(77.0));
        mostrar(sensor);
    }
}

/*
 * Salida esperada de consola:
 *   Temperatura: 25.0 °C
 * El proveedor legado entrega 77 °F y el adaptador convierte a Celsius.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AdaptadorTemperatura.java MainAdapter.java SensorCelsius.java TermometroLegado.java
 *   java codetrace.practica.adapter.MainAdapter
 * Salida esperada:
 *   Temperatura: 25.0 °C
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
