// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Comunicaciones del torneo: crear avisos para participantes.
// Patrón factory: Una fábrica devuelve un mensaje sin exponer el tipo concreto.
// Glosario: Torneo: competición entre varios equipos y partidas.
// Componente didáctico reutilizable: este archivo no tiene main.
// Compilar: en una copia desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

/** Contrato independiente: la fábrica devuelve esta abstracción. */
public interface Mensaje {
    String enviar();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (práctica)
 * Compilar todos los .java desde la carpeta java21 y ejecutar MainFactoryBasica.
 * Salida esperada:
 *   Correo a cliente@example.invalid
 * Este tipo no tiene main; su cliente demuestra el comportamiento.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainFactoryBasica
 * Nota: Mensaje.java es un componente; la salida corresponde al cliente MainFactoryBasica.java.
 * Salida esperada:
 *   Correo a cliente@example.invalid
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
