// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\\ct-java21" codetrace.practica.grafostipos.MainTiposGrafo
// Salida esperada: Dirigido acíclico (DAG): true; después de introducir un ciclo: false.
package codetrace.practica.grafostipos;

/** Orientación de las conexiones de un grafo, independiente de sus pesos. */
public enum Orientacion {
    DIRIGIDO, NO_DIRIGIDO
}
