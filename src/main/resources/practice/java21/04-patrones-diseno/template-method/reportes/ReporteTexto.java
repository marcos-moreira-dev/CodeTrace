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
