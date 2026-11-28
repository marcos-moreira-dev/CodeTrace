// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . AdaptadorTemperatura.java MainAdapter.java SensorCelsius.java TermometroLegado.java
// Ejecutar: java codetrace.practica.adapter.MainAdapter
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Termómetro antiguo del servidor que aloja partidas.
// Patrón adapter: Convertir una lectura antigua a la interfaz esperada.
// Glosario: Servidor de partida: computadora que coordina el juego.

package codetrace.practica.adapter;

import java.util.Objects;

/**
 * Adaptador de una interfaz externa a una interfaz del dominio.
 * No modifica la API del proveedor; convierte unidades antes de exponerlas.
 */
public final class AdaptadorTemperatura implements SensorCelsius {
    private final TermometroLegado proveedor;

    public AdaptadorTemperatura(TermometroLegado proveedor) {
        this.proveedor = Objects.requireNonNull(proveedor);
    }

    @Override
    public double leerCelsius() {
        return (proveedor.lecturaFahrenheit() - 32.0) * (5.0 / 9.0);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . AdaptadorTemperatura.java MainAdapter.java SensorCelsius.java TermometroLegado.java
 *   java codetrace.practica.adapter.MainAdapter
 * Nota: AdaptadorTemperatura.java es un componente; la salida corresponde al cliente MainAdapter.java.
 * Salida esperada:
 *   Temperatura: 25.0 °C
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
