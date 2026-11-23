// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Marcador de ronda que notifica resultados a los espectadores.
// Patrón observer: Varios observadores reciben cambios sin ser consultados continuamente.
// Glosario: Ronda: segmento breve de una partida; marcador: registro de resultados.
// Este archivo define exactamente un tipo reutilizable, sin main.
// Compilar: desde la carpeta java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

/** Ejemplo de consola: ejecutar MainObservadorBasico; salida esperada: consola=42 log=42. */

/** Contrato de notificación del observador básico. */
@FunctionalInterface
public interface Escucha {
    void alCambiar(int nuevoValor);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainObservadorBasico
 * Nota: Escucha.java es un componente; la salida corresponde al cliente MainObservadorBasico.java.
 * Salida esperada:
 *   consola=42 log=42
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
