// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Reglas de descuento en la inscripción del torneo.
// Patrón strategy: Intercambiar la política de cálculo sin alterar el cliente.
// Glosario: Torneo: competición de varias partidas entre equipos.
// Este archivo define exactamente un tipo reutilizable, sin main.
// Compilar: desde la carpeta java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

/** Ejemplo de consola: ejecutar MainStrategy; salida esperada: Normal: 100.0; Fidelidad: 90.0. */

/** Estrategia intercambiable: puede implementarse con una lambda. */
@FunctionalInterface
public interface Descuento {
    double aplicar(double total);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainStrategy
 * Nota: Descuento.java es un componente; la salida corresponde al cliente MainStrategy.java.
 * Salida esperada:
 *   Normal: 100.0
 *   Fidelidad: 90.0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
