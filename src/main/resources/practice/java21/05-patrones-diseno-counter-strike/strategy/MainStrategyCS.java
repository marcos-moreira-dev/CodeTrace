// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.strategy.MainStrategyCS
// Dominio: Counter-Strike — la economía virtual de rondas admite distintas políticas.
// Patrón: Strategy. Cada componente tiene su propia responsabilidad.
// Glosario: Eco = ronda de ahorro de créditos del juego; créditos = dinero virtual de la partida.
package codetrace.practica.counterstrike.strategy;

/** Cliente: elige una estrategia y la cambia durante la demostración. */
public final class MainStrategyCS {
    public static void main(String[] args) {
        GestorEconomia economia = new GestorEconomia(new PoliticaEco());
        // Misma cantidad de créditos, resultado diferente según la estrategia.
        System.out.println(economia.plan(1500));
        economia.cambiarPolitica(new PoliticaEquipo());
        System.out.println(economia.plan(1500));
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.strategy.MainStrategyCS
 * Nota: MainStrategyCS.java pertenece al ejercicio; el punto de entrada es MainStrategyCS.java.
 * Salida esperada:
 *   Eco: guardar 1500 créditos
 *   Equipo: asignar 1500 créditos virtuales
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
