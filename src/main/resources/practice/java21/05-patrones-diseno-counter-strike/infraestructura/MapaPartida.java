// Compilar (desde la raíz de 05-patrones-diseno-counter-strike): javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
// Ejecutar: java codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike, simulador narrativo de 3 rondas automáticas.
// Glosario: CT = antiterroristas; T = terroristas; PV = vida del jugador.
// Patrón de organización: infraestructura MVC, no un nuevo patrón GoF.
package codetrace.practica.counterstrike.infraestructura;

/**
 * Describe un escenario virtual, no un archivo ni una imagen del juego.
 * cs_office es un mapa conocido del modo rescate de Counter-Strike.
 * Sus zonas son etiquetas narrativas inventadas para este ejercicio.
 *
 * duracionRondaSegundos es una regla simplificada de nuestro simulador,
 * NO una afirmación de que todas las versiones del juego usen ese tiempo.
 */
public record MapaPartida(String codigo, String nombre,
                          String zonaRehenes, String zonaSegura,
                          int duracionRondaSegundos) {
    public MapaPartida {
        // El modo rescate necesita un escenario identificado, una zona de
        // objetivos, un destino de evacuación y un reloj inicial positivo.
        if (codigo == null || codigo.isBlank()
                || nombre == null || nombre.isBlank()
                || zonaRehenes == null || zonaRehenes.isBlank()
                || zonaSegura == null || zonaSegura.isBlank()
                || duracionRondaSegundos <= 0)
            throw new IllegalArgumentException("Mapa inválido");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde la raíz de 05-patrones-diseno-counter-strike:
 *   javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
 *   java codetrace.practica.counterstrike.MainCounterStrike
 * Salida esperada:
 *   Mapa: cs_office | Rescate de rehenes | Rondas: 3
 *   --- RONDA 1 | Reloj: 115 s ---
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
