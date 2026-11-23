// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\\ct-java21" codetrace.practica.grafostipos.MainTiposGrafo
// Salida esperada: Dirigido acíclico (DAG): true; después de introducir un ciclo: false.
package codetrace.practica.grafostipos;

/** Orientación de las conexiones de un grafo, independiente de sus pesos. */
public enum Orientacion {
    DIRIGIDO, NO_DIRIGIDO
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.grafostipos.MainTiposGrafo
 * Nota: Orientacion.java es un componente; la salida corresponde al cliente MainTiposGrafo.java.
 * Salida esperada:
 *   Dirigido acíclico (DAG): true
 *   Después de introducir un ciclo: false
 *   Árbol=true, bipartito=true, componentes=1
 *   Tras agregar un vértice aislado, componentes=2
 *   Triángulo completo=true, bipartito=false
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
