// Compilar (desde la raíz de 05-patrones-diseno-counter-strike): javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
// Ejecutar: java codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike, simulación narrativa del modo rescate de rehenes.
// Patrón: Facade como entrada al subsistema MVC de la partida.
// Glosario: CT/T = bandos; ronda = etapa de partida; rehén = NPC rescatable.
package codetrace.practica.counterstrike;

import codetrace.practica.counterstrike.facade.FachadaCounterStrike;

/**
 * Punto de entrada de la simulación automática de Counter-Strike.
 *
 * En Counter-Strike, entrar en una partida implica preparar el mapa,
 * los equipos, los objetivos de rescate, el marcador y la interfaz.
 * La fachada oculta esa coordinación al cliente: Main solo pide jugar.
 * MVC continúa trabajando detrás de ella; los trece clientes originales
 * de patrones siguen disponibles para estudiarlos por separado.
 */
public final class MainCounterStrike {
    public static void main(String[] args) {
        // La semilla 42 produce las mismas tres rondas en cualquier ejecución.
        // Así podemos comprobar el resultado sin pedir entradas por teclado.
        long semilla = 42L;

        // Como al iniciar una partida desde el menú del juego, el cliente
        // solicita una operación sin conocer todos los sistemas internos.
        // Facade prepara la vista y el controlador de una nueva sesión.
        FachadaCounterStrike counterStrike = new FachadaCounterStrike();

        // Se crean los equipos y los rehenes, se simulan tres rondas y se
        // publica el marcador final. El cliente no introduce comandos.
        counterStrike.jugarPartida(semilla);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde la raíz de 05-patrones-diseno-counter-strike:
 *   javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
 *   java codetrace.practica.counterstrike.MainCounterStrike
 * Salida esperada:
 *   === COUNTER-STRIKE | PARTIDA DIDÁCTICA ===
 *   Mapa: cs_office | Rescate de rehenes | Rondas: 3
 *   Equipos: CT (Astra, Nilo) vs T (Rook, Vega)
 *   Rehenes registrados: 2
 *   --- RONDA 1 | Reloj: 115 s ---
 *   [CADENA] Rescate: Elena llegó a Zona de extracción
 *   Marcador: CT 1 - 0 T
 *   --- RONDA 2 | Reloj: 115 s ---
 *   [TIENDA] Astra añadió MP9 | créditos=950 | capacidad ilustrativa=30
 *   [CADENA] Sin responsable: tiempo agotado sin rescate
 *   Marcador: CT 1 - 1 T
 *   --- RONDA 3 | Reloj: 115 s ---
 *   [CADENA] Rescate: Omar llegó a Zona de extracción
 *   Marcador: CT 2 - 1 T
 *   === FIN DE LA PARTIDA ===
 *   Resultado: Gana CT
 *   [REPORTE TEXTO] CT=2
 *   [REPORTE CSV] equipo,rondas;CT,2
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
