// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.command.MainCommandCS
// Dominio: Counter-Strike — el marcador permite registrar y deshacer resultados de ronda.
// Patrón: Command. Cada componente tiene su propia responsabilidad.
// Glosario: CT = equipo antiterrorista; ronda = etapa corta que suma un punto al equipo ganador.
package codetrace.practica.counterstrike.command;

/** Acción reversible: qué ejecutar y cómo deshacerla. */
public interface ComandoMarcador {
    void ejecutar();
    void deshacer();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.command.MainCommandCS
 * Nota: ComandoMarcador.java pertenece al ejercicio; el punto de entrada es MainCommandCS.java.
 * Salida esperada:
 *   Tras ejecutar: CT=1
 *   Tras deshacer: CT=0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
