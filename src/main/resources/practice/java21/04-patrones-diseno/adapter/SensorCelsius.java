// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Termómetro antiguo del servidor que aloja partidas.
// Patrón adapter: Convertir una lectura antigua a la interfaz esperada.
// Glosario: Servidor de partida: computadora que coordina el juego.
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: ejecutar desde el archivo cliente del mismo ejercicio

package codetrace.practica.adapter;

/** Contrato que espera el código cliente, independiente del proveedor. */
public interface SensorCelsius {
    double leerCelsius();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.adapter.MainAdapter
 * Nota: SensorCelsius.java es un componente; la salida corresponde al cliente MainAdapter.java.
 * Salida esperada:
 *   Temperatura: 25.0 °C
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
