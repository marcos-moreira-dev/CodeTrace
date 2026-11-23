// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.observer.MainObserverCS
// Dominio: Counter-Strike — la puntuación de las rondas se informa a los espectadores.
// Patrón: Observer. Cada componente tiene su propia responsabilidad.
// Glosario: CT = equipo antiterrorista del juego; T = equipo terrorista del juego; HUD = indicadores visibles en pantalla.
package codetrace.practica.counterstrike.observer;

import java.util.ArrayList;
import java.util.List;

/** Sujeto observable: conoce a sus suscriptores, no sus pantallas concretas. */
public final class MarcadorRonda {
    private final List<ObservadorPuntuacion> observadores = new ArrayList<>();

    public void suscribir(ObservadorPuntuacion observador) {
        // Guardamos cada suscripción para futuras actualizaciones de ronda.
        observadores.add(observador);
    }

    public void anotar(String equipo, int puntos) {
        // Este ciclo notifica a TODOS, a diferencia de Chain of Responsibility.
        for (ObservadorPuntuacion observador : observadores)
            observador.actualizar(equipo, puntos);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.observer.MainObserverCS
 * Nota: MarcadorRonda.java pertenece al ejercicio; el punto de entrada es MainObserverCS.java.
 * Salida esperada:
 *   HUD: CT=2
 *   HUD: T=1
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
