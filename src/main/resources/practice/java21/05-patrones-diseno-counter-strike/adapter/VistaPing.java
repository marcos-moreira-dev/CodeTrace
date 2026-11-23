// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.adapter.MainAdapterCS
// Dominio: Counter-Strike — el servidor de la partida informa la latencia en microsegundos pero el HUD espera milisegundos.
// Patrón: Adapter; ejercicio independiente, no oficial.
// Glosario: Ping = latencia de la conexión; ms = milisegundos; microsegundo = milésima parte de un ms.
package codetrace.practica.counterstrike.adapter;

/** Interfaz que espera la pantalla moderna de Counter-Strike. */
public interface VistaPing { String mostrarPing(); }

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.adapter.MainAdapterCS
 * Nota: VistaPing.java se estudia junto al cliente MainAdapterCS.java.
 * Salida esperada:
 *   Ping: 42 ms
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
