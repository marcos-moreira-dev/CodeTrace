// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . MainObserverCS.java MarcadorRonda.java ObservadorPuntuacion.java PantallaEspectador.java
// Ejecutar: java codetrace.practica.counterstrike.observer.MainObserverCS
// Dominio: Counter-Strike — la puntuación de las rondas se informa a los espectadores.
// Patrón: Observer. Cada componente tiene su propia responsabilidad.
// Glosario: CT = equipo antiterrorista del juego; T = equipo terrorista del juego; HUD = indicadores visibles en pantalla.
package codetrace.practica.counterstrike.observer;

/** Cliente: conecta el marcador con el HUD sin actualizaciones manuales. */
public final class MainObserverCS {
    public static void main(String[] args) {
        MarcadorRonda marcador = new MarcadorRonda();
        marcador.suscribir(new PantallaEspectador());

        // Simulamos dos cambios del marcador; el observador imprime ambos.
        marcador.anotar("CT", 2);
        marcador.anotar("T", 1);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainObserverCS.java MarcadorRonda.java ObservadorPuntuacion.java PantallaEspectador.java
 *   java codetrace.practica.counterstrike.observer.MainObserverCS
 * Nota: MainObserverCS.java pertenece al ejercicio; el punto de entrada es MainObserverCS.java.
 * Salida esperada:
 *   HUD: CT=2
 *   HUD: T=1
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
