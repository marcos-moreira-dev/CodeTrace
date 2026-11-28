// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . ConfiguracionSala.java ConfiguradorSala.java MainBuilderCS.java
// Ejecutar: java codetrace.practica.counterstrike.builder.MainBuilderCS
// Dominio: Counter-Strike — el anfitrión configura una sala privada de Counter-Strike por pasos.
// Patrón: Builder; ejercicio independiente, no oficial.
// Glosario: Sala = partida personalizada; mapa = escenario virtual; rehén = personaje virtual que puede ser objetivo de rescate.
package codetrace.practica.counterstrike.builder;

/** Resultado inmutable de la construcción: tres opciones de la sala virtual. */
public record ConfiguracionSala(String mapa, int rondas, boolean rescateRehenes) {
    public ConfiguracionSala {
        if (mapa == null || mapa.isBlank() || rondas <= 0)
            throw new IllegalArgumentException("Configuración inválida");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ConfiguracionSala.java ConfiguradorSala.java MainBuilderCS.java
 *   java codetrace.practica.counterstrike.builder.MainBuilderCS
 * Nota: ConfiguracionSala.java se estudia junto al cliente MainBuilderCS.java.
 * Salida esperada:
 *   Sala: cs_office, rondas=12, rehenes=true
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
