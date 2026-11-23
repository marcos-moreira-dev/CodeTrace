// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.template.MainTemplateCS
// Dominio: Counter-Strike — un resumen de resultados de Counter-Strike se imprime en texto o CSV.
// Patrón: Template Method; ejemplo didáctico independiente.
// Glosario: CT = equipo antiterrorista; CSV = datos separados por coma; mapa = escenario virtual.
package codetrace.practica.counterstrike.template;

/** Variante concreta: informa el mapa y el marcador como frase. */
public final class ReporteTexto extends ProcesoReporte {
    @Override protected String encabezado(String mapa) {
        return "Partida " + mapa + ": ";
    }
    @Override protected String detalle(int rondasCt) {
        return "CT=" + rondasCt;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.template.MainTemplateCS
 * Nota: ReporteTexto.java pertenece al ejercicio; MainTemplateCS.java es su cliente.
 * Salida esperada:
 *   Texto: Partida de_dust2: CT=7
 *   CSV: mapa,rondasCt;de_dust2,7
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
