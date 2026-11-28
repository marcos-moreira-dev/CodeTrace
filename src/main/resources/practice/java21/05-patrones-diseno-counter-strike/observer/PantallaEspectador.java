// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainObserverCS.java MarcadorRonda.java ObservadorPuntuacion.java PantallaEspectador.java
// Ejecutar: java codetrace.practica.counterstrike.observer.MainObserverCS
// Dominio: Counter-Strike — la puntuación de las rondas se informa a los espectadores.
// Patrón: Observer. Cada componente tiene su propia responsabilidad.
// Glosario: CT = equipo antiterrorista del juego; T = equipo terrorista del juego; HUD = indicadores visibles en pantalla.
package codetrace.practica.counterstrike.observer;

/** Observador concreto: representa el HUD de un espectador virtual. */
public final class PantallaEspectador implements ObservadorPuntuacion {
    @Override public void actualizar(String equipo, int puntos) {
        System.out.println("HUD: " + equipo + "=" + puntos);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainObserverCS.java MarcadorRonda.java ObservadorPuntuacion.java PantallaEspectador.java
 *   java codetrace.practica.counterstrike.observer.MainObserverCS
 * Nota: PantallaEspectador.java pertenece al ejercicio; el punto de entrada es MainObserverCS.java.
 * Salida esperada:
 *   HUD: CT=2
 *   HUD: T=1
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
