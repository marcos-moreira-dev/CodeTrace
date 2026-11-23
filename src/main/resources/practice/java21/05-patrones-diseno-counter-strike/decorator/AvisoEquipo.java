// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.decorator.MainDecoratorCS
// Dominio: Counter-Strike — mensajes del equipo que reciben etiquetas informativas.
// Patrón: Decorator; ejercicio educativo no oficial.
// Glosario: Ronda = etapa breve de partida; rehén = personaje virtual que puede rescatarse.
package codetrace.practica.counterstrike.decorator;

/** Componente común: devolver el texto final del mensaje del juego. */
public interface AvisoEquipo { String texto(); }

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.decorator.MainDecoratorCS
 * Nota: AvisoEquipo.java pertenece al ejercicio; el cliente es MainDecoratorCS.java.
 * Salida esperada:
 *   [RONDA 12] [URGENTE] Rehén virtual rescatado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
