// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.puntuacion.MainPuntuacionCS
// Dominio: Counter-Strike — comparar puntuaciones de torneos casuales y competitivos.
// Patrón: Strategy (variante de tarifas); ejemplo didáctico independiente.
// Glosario: Casual = modo más relajado; competitivo = encuentro con reglas de clasificación; victoria = ronda ganada.
package codetrace.practica.counterstrike.puntuacion;

/** Contrato de algoritmo: el mismo número de victorias da distintos puntos. */
@FunctionalInterface
public interface ReglaPuntos {
    int calcular(int victorias);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.puntuacion.MainPuntuacionCS
 * Nota: ReglaPuntos.java pertenece al ejercicio; MainPuntuacionCS.java es su cliente.
 * Salida esperada:
 *   Casual: 4 puntos
 *   Competitivo: 6 puntos
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
