// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Tarifas de inscripción al torneo según la categoría.
// Patrón strategy-tarifas: Cambiar la política de cálculo sin modificar el cliente.
// Glosario: Inscripción: registro de un participante o equipo al torneo.
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
package codetrace.practica.tarifas;

/** Contrato del cálculo, independiente de quien lo utilice. */
@FunctionalInterface
public interface EstrategiaTarifa {
    double total(double subtotal);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.tarifas.MainTarifas
 * Nota: EstrategiaTarifa.java es un componente; la salida corresponde al cliente MainTarifas.java.
 * Salida esperada:
 *   100.0
 *   85.0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
