// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . AdaptadorTemperatura.java MainAdapter.java SensorCelsius.java TermometroLegado.java
// Ejecutar: java codetrace.practica.adapter.MainAdapter
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Termómetro antiguo del servidor que aloja partidas.
// Patrón adapter: Convertir una lectura antigua a la interfaz esperada.
// Glosario: Servidor de partida: computadora que coordina el juego.

package codetrace.practica.adapter;

/**
 * API heredada hipotética: su unidad es Fahrenheit, no Celsius.
 * Simula un proveedor cuyo contrato no se puede cambiar.
 */
public final class TermometroLegado {
    private final double fahrenheit;

    public TermometroLegado(double fahrenheit) {
        this.fahrenheit = fahrenheit;
    }

    public double lecturaFahrenheit() { return fahrenheit; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AdaptadorTemperatura.java MainAdapter.java SensorCelsius.java TermometroLegado.java
 *   java codetrace.practica.adapter.MainAdapter
 * Nota: TermometroLegado.java es un componente; la salida corresponde al cliente MainAdapter.java.
 * Salida esperada:
 *   Temperatura: 25.0 °C
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
