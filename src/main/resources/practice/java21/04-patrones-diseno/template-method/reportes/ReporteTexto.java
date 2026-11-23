// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\\ct-java21" codetrace.practica.patrones.templatemethod.MainTemplateMethod
// Salida esperada: == ventas == / Total: 15 / titulo,total / ventas,15
package codetrace.practica.patrones.templatemethod;

/** Variante concreta: personaliza las fases del reporte de texto. */
public final class ReporteTexto extends ProcesoReporte {
    @Override protected String encabezado(String titulo) {
        return "== " + titulo + " ==\n";
    }
    @Override protected String detalle(int total) {
        return "Total: " + total;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.templatemethod.MainTemplateMethod
 * Nota: ReporteTexto.java es un componente; la salida corresponde al cliente MainTemplateMethod.java.
 * Salida esperada:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
