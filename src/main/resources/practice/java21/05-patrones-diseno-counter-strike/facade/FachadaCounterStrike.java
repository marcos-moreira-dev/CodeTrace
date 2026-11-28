// CodeTrace | Referencia: Java 21 / JDK 21
// Desde la raíz de 05-patrones-diseno-counter-strike.
// Compilar: javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
// Ejecutar: java codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike, inicio de una sesión automática de rescate.
// Patrón: Facade (Fachada); interfaz sencilla ante el subsistema de partida.
// Glosario: NPC = personaje no jugable; HUD = información de pantalla.
package codetrace.practica.counterstrike.facade;

import codetrace.practica.counterstrike.infraestructura.ControladorPartida;
import codetrace.practica.counterstrike.infraestructura.EstadoPartida;
import codetrace.practica.counterstrike.infraestructura.VistaConsola;

/**
 * Fachada del simulador: el cliente pide jugar una partida de Counter-Strike
 * sin tener que ensamblar por separado los subsistemas del modelo y la vista.
 *
 * Facade no reemplaza MVC ni decide quién gana. Su responsabilidad consiste
 * en ofrecer una operación de alto nivel para iniciar una sesión completa.
 * MainCounterStrike es el cliente real de este patrón.
 */
public final class FachadaCounterStrike {
    public EstadoPartida jugarPartida(long semilla) {
        // Cada llamada abre una sesión nueva: la vista relatará los hechos
        // y el controlador creará los equipos, rehenes y marcador en memoria.
        // Así no se mezclan los créditos ni rescates de partidas anteriores.
        VistaConsola vista = new VistaConsola();

        // El controlador recibe la vista y una semilla para que los
        // desenlaces de las rondas sean reproducibles al probar el código.
        ControladorPartida controlador = new ControladorPartida(vista, semilla);

        // Delegamos el ciclo completo al controlador. Al terminar devolvemos
        // el modelo final por si otro cliente quiere consultar el marcador
        // o el historial, sin tener que interpretar las líneas de consola.
        return controlador.simular();
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde la raíz de 05-patrones-diseno-counter-strike:
 *   javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
 *   java codetrace.practica.counterstrike.MainCounterStrike
 * Salida esperada:
 *   === COUNTER-STRIKE | PARTIDA DIDÁCTICA ===
 *   [CADENA] Rescate: Elena llegó a Zona de extracción
 *   Marcador: CT 1 - 0 T
 *   Marcador: CT 1 - 1 T
 *   [CADENA] Rescate: Omar llegó a Zona de extracción
 *   Marcador: CT 2 - 1 T
 *   Resultado: Gana CT
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
