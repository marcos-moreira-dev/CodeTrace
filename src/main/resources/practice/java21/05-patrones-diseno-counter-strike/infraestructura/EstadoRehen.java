// CodeTrace | Referencia: Java 21 / JDK 21
// Desde la raíz de 05-patrones-diseno-counter-strike.
// Compilar: javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
// Ejecutar: java codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike, simulador narrativo de 3 rondas automáticas.
// Glosario: CT = antiterroristas; T = terroristas; PV = vida del jugador.
// Patrón de organización: infraestructura MVC, no un nuevo patrón GoF.
package codetrace.practica.counterstrike.infraestructura;

/**
 * Estado de un personaje no jugable (NPC) en el modo rescate:
 * RETENIDO: está todavía en el escenario sin escolta asignada.
 * ESCOLTADO: un personaje CT está acompañándolo hacia la zona segura.
 * RESCATADO: alcanzó el 100 % del trayecto virtual de la simulación.
 *
 * Es un modelo narrativo sencillo, sin animaciones ni rutas de IA.
 */
public enum EstadoRehen { RETENIDO, ESCOLTADO, RESCATADO }

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde la raíz de 05-patrones-diseno-counter-strike:
 *   javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
 *   java codetrace.practica.counterstrike.MainCounterStrike
 * Salida esperada:
 *   [REHÉN] Elena | ubicación=Archivo de oficinas | calma=50/100 | integridad=92/100 | escolta=Astra | progreso=100% | estado=RESCATADO
 *   [REHÉN] Omar | ubicación=Sala de juntas | calma=36/100 | integridad=86/100 | escolta=Astra | progreso=100% | estado=RESCATADO
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
