// CodeTrace | Referencia: Java 21 / JDK 21
// Desde la raíz de 05-patrones-diseno-counter-strike.
// Compilar: javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
// Ejecutar: java codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike, simulador narrativo de 3 rondas automáticas.
// Glosario: CT = antiterroristas; T = terroristas; PV = vida del jugador.
// Patrón de organización: infraestructura MVC, no un nuevo patrón GoF.
package codetrace.practica.counterstrike.infraestructura;

/**
 * Counter-Strike representa dos bandos en una partida por equipos.
 * CT significa "antiterroristas" y T, "terroristas", dentro del videojuego.
 * Usamos enum porque un jugador no puede cambiar arbitrariamente de bando.
 */
public enum Bando {
    CT("Antiterroristas"), T("Terroristas");

    private final String descripcion;
    Bando(String descripcion) { this.descripcion = descripcion; }
    public String descripcion() { return descripcion; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde la raíz de 05-patrones-diseno-counter-strike:
 *   javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
 *   java codetrace.practica.counterstrike.MainCounterStrike
 * Salida esperada:
 *   Equipos: CT (Astra, Nilo) vs T (Rook, Vega)
 *   Marcador: CT 2 - 1 T
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
