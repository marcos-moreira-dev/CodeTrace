// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike, simulación narrativa del modo rescate de rehenes.
// Patrón de organización: MVC. Aquí solo comienza el ejemplo integrador.
// Glosario: CT/T = bandos; ronda = etapa de partida; rehén = NPC rescatable.
package codetrace.practica.counterstrike;

import codetrace.practica.counterstrike.infraestructura.ControladorPartida;
import codetrace.practica.counterstrike.infraestructura.VistaConsola;

/**
 * Punto de entrada de la simulación automática de Counter-Strike.
 *
 * El Main no debería controlar rehenes, sumar puntos ni imprimir cada
 * evento: esos trabajos pertenecen al controlador, modelo y vista.
 * Los trece ejemplos de patrones de la carpeta 05 siguen ejecutándose
 * por separado; aquí reutilizamos sus COLABORADORES.
 */
public final class MainCounterStrike {
    public static void main(String[] args) {
        // La semilla 42 produce las mismas tres rondas en cualquier ejecución.
        // Así podemos comprobar el resultado sin pedir entradas por teclado.
        long semilla = 42L;

        // Inyectamos la vista de consola al controlador: la vista solo relata
        // el estado y no decide quién gana una ronda de Counter-Strike.
        VistaConsola vista = new VistaConsola();
        ControladorPartida controlador = new ControladorPartida(vista, semilla);

        // La simulación avanza automáticamente por tres rondas y termina.
        // Ninguna clase abre una ventana ni espera un Scanner.
        controlador.simular();
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.MainCounterStrike
 * La ejecución no espera entrada; semilla=42 y tres rondas automáticas.
 * Salida esperada:
 *   === COUNTER-STRIKE | PARTIDA DIDÁCTICA ===
 *   Semilla: 42 (misma semilla = misma secuencia)
 *   Mapa: cs_office | Rescate de rehenes | Rondas: 3
 *   [RED] Ping: 42 ms
 *   === FIN DE LA PARTIDA ===
 *   Resultado: Gana CT
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
