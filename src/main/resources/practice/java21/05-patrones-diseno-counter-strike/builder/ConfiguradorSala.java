// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . ConfiguracionSala.java ConfiguradorSala.java MainBuilderCS.java
// Ejecutar: java codetrace.practica.counterstrike.builder.MainBuilderCS
// Dominio: Counter-Strike — el anfitrión configura una sala privada de Counter-Strike por pasos.
// Patrón: Builder; ejercicio independiente, no oficial.
// Glosario: Sala = partida personalizada; mapa = escenario virtual; rehén = personaje virtual que puede ser objetivo de rescate.
package codetrace.practica.counterstrike.builder;

/** Builder: acumula opciones y construye la configuración solo al final. */
public final class ConfiguradorSala {
    private String mapa = "de_dust2";
    private int rondas = 6;
    private boolean rescateRehenes;

    public ConfiguradorSala mapa(String nuevoMapa) {
        this.mapa = nuevoMapa;
        return this; // Permite encadenar configurador.mapa(...).rondas(...).
    }
    public ConfiguradorSala rondas(int cantidad) {
        this.rondas = cantidad;
        return this;
    }
    public ConfiguradorSala rescateRehenes(boolean habilitar) {
        this.rescateRehenes = habilitar;
        return this;
    }
    public ConfiguracionSala construir() {
        // Aquí se valida la configuración final mediante el constructor del record.
        return new ConfiguracionSala(mapa, rondas, rescateRehenes);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ConfiguracionSala.java ConfiguradorSala.java MainBuilderCS.java
 *   java codetrace.practica.counterstrike.builder.MainBuilderCS
 * Nota: ConfiguradorSala.java se estudia junto al cliente MainBuilderCS.java.
 * Salida esperada:
 *   Sala: cs_office, rondas=12, rehenes=true
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
